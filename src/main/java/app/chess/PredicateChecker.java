package app.chess;

import app.chess.moves.Castle;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.chess.moves.Promotion;
import app.chess.pieces.ChessPieceFactory;
import app.chess.utils.Utils;

import java.util.function.Function;

final class PredicateChecker {
    private PredicateChecker() {
    }

    /**
     * A function that performs a given move, checks predicate, rolls back said move and then returns result of said
     * predicate. Please note that internal states of pieces/pawns are also going to be modified while performing the
     * move (but they will be rolled back as well).
     *
     * @param predicate A predicate to be checked for; MUST NOT modify board in any way. If it does modify
     *         board, its predicate's responsibility to properly roll it back (including ChessPieces internal states).
     * @param move A legal move that is to be performed. Note that there are no checks performed, if illegal
     *         move is passed the result is going to be undefined behaviour.
     * @param board A board on which to perform move. It should match the first argument of the predicate,
     *         otherwise it makes absolutely no sense.
     * @param manager StateManager of the game.
     * @param <T> Return type of the predicate.
     * @return The result of performing the predicate on the modified board.
     */
    public static <T> T safelyCheckPredicate(
            Function<ChessPiece[][], T> predicate, ChessMove move, ChessPiece[][] board, StateManager manager,
            Mover mover
    ) {

        //Unfortunately, we need to pretty much split the code in 3 branches, accounting for all possible moves
        //Normal moves, castling and promotion.

        if (move.getClass() == NormalMove.class) {
            return checkPredicateForNormalMove(predicate, move, board, manager, mover);
        } else if (move.getClass() == Castle.class) {
            return checkPredicateForCastling(predicate, (Castle) move, board, manager, mover);
        } else if (move.getClass() == Promotion.class) {
            return checkPredicateForPiecePick(predicate, move, board, manager, mover);
        } else {
            //It shouldn't happen, but if that happens it means that somebody decided to add a new move to the game
            //And didn't update this function to account for it. Quite important to throw exception in such situation, I believe
            throw new UnsupportedMove();
        }
    }


    private static <T> T checkPredicateForNormalMove(
            Function<ChessPiece[][], T> predicate, ChessMove move, ChessPiece[][] board, StateManager manager,
            Mover mover
    ) {
        //Saving copies of pieces that are going to be changed in case of the normal move
        ChessPiece killedPiece = mover.getPieceKilledByMove(move, board);
        ChessPiece movePerformer = move.getPiece();

        ChessPiece killedPieceCopy = ChessPieceFactory.copyPiece(killedPiece);
        //Note that killedPieceCopy might be null and that's totally okay
        ChessPiece movePerformerCopy = ChessPieceFactory.copyPiece(movePerformer);
        //We'd also like to backup the state manager, as it will be changed by performing of the move
        StateManager managerCopy = new StateManager(manager);

        //Now with possibly changed pieces we need to execute the move
        mover.makeMove(move.getPiece().getPlayer(), move, board, manager);

        //We calculate the result of the predicate
        T predicateResult = predicate.apply(board);

        //Now we roll everything back
        manager.copyState(managerCopy);

        //Now we roll back the internal state of pieces that were here before
        //Please note that we CANNOT put copies on the board, because every class that holds a reference to those pieces will be in a trouble.
        if (killedPiece != null) {
            killedPiece.unwrap().overwriteState(killedPieceCopy.unwrap());
        }
        movePerformer.unwrap().overwriteState(movePerformerCopy.unwrap());

        //We remove the piece that was placed on said field
        Utils.putPieceOnBoard(null, move.getField(), board);

        //Put back the piece that was killed (it doesn't have to be the same field, google en passant)
        if (killedPiece != null) {
            Utils.putPieceOnBoard(killedPiece, killedPiece.getPosition(), board);
        }
        Utils.putPieceOnBoard(movePerformer, movePerformer.getPosition(), board);

        return predicateResult;
    }

    private static <T> T checkPredicateForCastling(
            Function<ChessPiece[][], T> predicate, Castle move, ChessPiece[][] board, StateManager manager, Mover mover
    ) {
        //If you need an explanation of what I'm doing here, please look at the function that takes care of checking predicates
        //for normal moves. It's pretty analogical, although it wasn't really possible to reuse code from that function here

        ChessPiece king = move.getPiece();
        ChessPiece rook = Utils.getPieceByField(Utils.getRookPositionBasedOnCastling(move), board);

        ChessPiece kingCopy = ChessPieceFactory.copyPiece(king);
        ChessPiece rookCopy = ChessPieceFactory.copyPiece(rook);

        StateManager copyManager = new StateManager(manager);

        mover.makeMove(king.getPlayer(), move, board, manager);

        T predicateResult = predicate.apply(board);

        manager.copyState(copyManager);

        king.unwrap().overwriteState(kingCopy.unwrap());
        rook.unwrap().overwriteState(rookCopy.unwrap());

        Utils.putPieceOnBoard(null, move.getField(), board);
        Utils.putPieceOnBoard(null, Utils.inferNewRookPositionAfterCastling(move), board);

        Utils.putPieceOnBoard(king, king.getPosition(), board);
        Utils.putPieceOnBoard(rook, rook.getPosition(), board);

        return predicateResult;
    }

    private static <T> T checkPredicateForPiecePick(
            Function<ChessPiece[][], T> predicate, ChessMove move, ChessPiece[][] board, StateManager manager,
            Mover mover
    ) {
        ChessPiece pawn = Utils.getPieceByField(move.getField(), board);
        ChessPiece pawnCopy = ChessPieceFactory.copyPiece(pawn);

        //We cannot do move.getPiece() because it would return a new piece that is going to be put on that field
        StateManager copyManager = new StateManager(manager);

        mover.makeMove(pawn.getPlayer(), move, board, manager);

        T predicateResult = predicate.apply(board);

        manager.copyState(copyManager);

        pawn.unwrap().overwriteState(pawnCopy.unwrap());

        Utils.putPieceOnBoard(pawn, pawn.getPosition(),
                              board); //Now we put pawn there again, effectively removing the piece that was promoted

        return predicateResult;
    }

    static class UnsupportedMove extends RuntimeException {
    }
}
