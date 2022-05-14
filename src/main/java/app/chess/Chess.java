package app.chess;

import app.chess.moves.Castle;
import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPieceFactory;
import app.chess.pieces.ChessPieceKind;
import app.chess.rules.*;
import app.chess.utils.*;
import app.core.game.Field;
import app.core.game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static app.chess.pieces.ChessPieceKind.KING;

public class Chess implements Game<ChessMove, ChessPiece> {
    public static final int SIZE = 8;
    ChessPiece[][] board;
    private boolean blackToMove = false;
    private boolean pendingPromotion = false; //In case there is a promotion of a pawn, that move is split into 2 submoves
    private boolean testMode = false; //this is NOT how it should be done, but it's the simplest way

    private final Validator validator = new StandardValidator();

    public Chess(Board board) {
        this.board = board.pieces;
    }




    private boolean validateKingSafety(int player) {
        //Validates king safety the way that is done in validateKingSafety(ChessMove move) but it doesn't perform any move
        //Yes, it's duplicating some parts of code, but we will have to live with it for now

        int secondPlayer = player == 0 ? 1 : 0;

        try {
            testMode = true;
            getLegalMoves(secondPlayer);
        } catch (KingCanBeTaken e) {
            return false;
        } finally {
            testMode = false;
        }
        return true;
    }



    private boolean validateMove(Castle move) {
        //There are 6 conditions for castling to be valid and checking for some of them is going to be annoying to say the least

        //First: King cannot be in check
        if (!testMode && !validateKingSafety(move.getPiece().getPlayer())) {
            return false;
        }

        //Second: Rook and king haven't moved since beginning of the game

        final int currentRank = move.getPiece().getPosition().rank();
        final int currentFile = move.getPiece().getPosition().file();

        final int newRank = move.getField().rank();
        final int newFile = move.getField().file();

        final boolean kingSideCastling = newFile - currentFile > 0;  //If the file is increasing, this means (both for white and black) that we are castling king side

        final int rookRank = move.getPiece().getPlayer() == 0 ? 1 : 8; //White has rooks on first rank, black on eighth
        final int rookFile = kingSideCastling ? 8 : 1;

        var rook = board[rookRank][rookFile]; //You might ask why I've decided to call that variable "rook" when I have no guarantee that such piece is a rook. Well, I'm checking that in the if that is below

        if (rook == null || !rook.unwrap().canParticipateInCastling()) {
            //There's no rook to even, you know, castle with
            //Or something that on that place cannot castle
            return false;
        }
        //You might ask: Why is that sufficient? Well, only rooks and kings can ever participate in castling, and any move invalidates that right.
        //That pretty much means that whatever is standing on that place has been standing there for all of the game, so it is eligible for castling

        //It'd be great to check what's going on with our king
        //We have a guarantee that the piece that tries to castle is king, because we are checking that in Castle constructor

        if (!move.getPiece().unwrap().canParticipateInCastling()) {
            return false;
        }

        //So, our rook didn't move and our king also didn't
        //That's perfect, second condition is satisfied

        //Third condition: Road between rook and king is not obstructed
        if (!Utils.roadNotObstructed(move.getPiece().getPosition(), new Field(rookRank, rookFile), board)) {
            return false;
        }

        //Fourth condition: Nothing on the road of the king is under attack
        int multiplier = kingSideCastling ? 1 : -1;

        for (int i = 1; i <= 2; i++) {
            //This is somewhat weird, but we are going to check if this is ok by placing a fake king on a piece that cannot be attacked and checking if anything goes wrong
            //Truly magnificent
            //(and crazy ineffective, but it was never meant to be effective)
            var currentPiece = board[currentRank][currentFile + i * multiplier];
            if(currentPiece == null){
                continue;
            }
            var field = new Field(currentRank, currentFile + i * multiplier);
            board[field.rank()][field.file()] = ChessPieceFactory.newPiece(field, KING, currentPiece.getColor());

            if (!testMode && !validateKingSafety(move.getPiece().getPlayer())) {
                board[currentRank][currentFile + i * multiplier] = currentPiece;
                return false;
            }
            board[currentRank][currentFile + i * multiplier] = currentPiece;
        }

        return true;
    }


    @Override
    public List<ChessPiece> getPieces(int player) {
        return Utils.getMatchingPieces(true, player, board);
    }

    @Override
    public List<ChessPiece> getAllPieces() {
        return Utils.getMatchingPieces(false, 0, board);
    }

    private List<ChessMove> getPromotionMoves(int player) {
        return Collections.emptyList();
    }

    @Override
    public List<ChessMove> getLegalMoves(int player) {

        if (pendingPromotion) {
            if (true)
                throw new BoardDiscrepancy();
            //Promotion is this funny case where we should create entirely different branch
            pendingPromotion = false;
            return getPromotionMoves(player);
        }

        List<ChessPiece> playersPieces = getPieces(player);

        List<ChessMove> allLegalMoves = new ArrayList<>();

        for (var currentPiece : playersPieces) {
            List<ChessMove> someLegalMoves = validator.getLegalMoves(currentPiece, board);
            allLegalMoves.addAll(someLegalMoves);
        }

        return allLegalMoves;
    }

    @Override
    public List<ChessMove> getLegalMoves(int player, ChessPiece piece) {
        return validator.getLegalMoves(piece,board);
    }

    private void resetWasMoved() {
        var allPieces = getAllPieces();

        for (var currentPiece : allPieces) {
            currentPiece.unwrap().resetMoved();
        }
    }

    /**
     * Executes castling under assumption that function calling it made sure that such castling is, indeed, legal
     *
     * @param move
     * @return
     */
    private List<ChessPiece> castleMove(Castle move) {
        Field whereRookIs = Utils.getRookPositionBasedOnCastling(move, board);

        int rookRank = whereRookIs.rank();
        int rookFile = whereRookIs.file();

        //Castling can't kill anything, etc.
        //Just changing positions of 2 pieces, nothing can go wrong
        //Right?

        var king = move.getPiece();
        int kingRank = king.getPosition().rank(); //Yes, kingRank will be the same as rookRank
        //In fact, let's just have an additional assertion here

        if (kingRank != rookRank) {
            throw new BoardDiscrepancy();
        }

        int kingFile = king.getPosition().file();

        var rook = board[rookRank][rookFile];

        int newRookFile = rookFile == 1 ? 4 : 6; //It is just this way
        //Rank will stay the same, obviously

        board[rookRank][rookFile] = null;
        board[kingRank][kingFile] = null;

        board[move.getField().rank()][move.getField().file()] = king;
        king.unwrap().move(move.getField());

        board[rookRank][newRookFile] = rook;
        rook.unwrap().move(new Field(rookRank, newRookFile));

        return List.of(move.getPiece(), rook);
    }

    @Override
    public List<ChessPiece> makeMove(int player, ChessMove move) {

        if (move.getPiece().getPlayer() != player) {
            throw new PlayerDiscrepancy();
        }


        //It's better to be sure that the move we get is actually legal
        List<ChessMove> acceptableMoves = this.getLegalMoves(player, move.getPiece());
        if (!acceptableMoves.contains(move)) {
            System.err.println(acceptableMoves);
            System.err.println(move);
            throw new IllegalMove();
        }


        ///TODO: MAKE IT NOT FAIL WITH PENDING PROMOTION
        blackToMove = !blackToMove;


        //Now, if castling is involved, I'm going to delegate it to another function because otherwise this will be to messy
        if (move.getClass() == Castle.class) {
            //I'm sure checking it that way won't cause any issues down the line
            return castleMove((Castle) move);
        }

        if (pendingPromotion) {
            ///TODO: IMPLEMENT PROMOTION
            pendingPromotion = false;
        }

        resetWasMoved(); //Because things will be overwritten

        //Given the fact that this move is legal, we can simply execute it now
        if (move.getPiece().getKind() == ChessPieceKind.PAWN && (move.getField().rank() == SIZE || move.getField()
                                                                                                       .rank() == 1)) {
            //That's funny because it will result in a pawn promotion
            if (pendingPromotion) {
                throw new RuntimeException("This shouldn't even be mathematically possible");
            }
            ///TODO: IMPLEMENT PROMOTION
            //pendingPromotion = true;
        }

        int newRank = move.getField().rank();
        int newFile = move.getField().file();

        int oldRank = move.getPiece().getPosition().rank();
        int oldFile = move.getPiece().getPosition().file();

        var wasHereBefore = board[newRank][newFile];

        if (wasHereBefore != null && wasHereBefore.getPlayer() == player) {
            throw new IllegalMove(); //This shouldn't even be possible because we've validated that already, but just to be sure
        }

        if (wasHereBefore != null) {
            wasHereBefore.unwrap().kill();
            board[newRank][newFile] = move.getPiece();
        } else {
            board[newRank][newFile] = move.getPiece();

            //you'd think that we are done
            //and you'd be wrong
            //because en passant exists
            //to mess with our code

            if (move.getPiece().getKind() == ChessPieceKind.PAWN && oldFile - newFile != 0) {
                //So, there is nothing in there but we are going sideways
                //This means en passant
                //So we'll just remove whatever stays in enpassantable place
                wasHereBefore = board[oldRank][newFile];
                if (wasHereBefore == null) {
                    //?????????
                    throw new IllegalMove();
                } else {
                    wasHereBefore.unwrap().kill();
                    board[oldRank][newFile] = null;
                }
            }

        }


        board[oldRank][oldFile] = null;

        move.getPiece().unwrap().move(move.getField());

        //After executing the move, we can finally return a list informing what's happening on the board
        ArrayList<ChessPiece> changedList = new ArrayList<>();
        changedList.add(move.getPiece());
        if (wasHereBefore != null) {
            changedList.add(wasHereBefore);
        }

        return changedList;
    }

    @Override
    public int getPlayerCount() {
        return 2; //It's always going to be that way in classical chess
    }

    static class IllegalMove extends RuntimeException {
    }

    static class PlayerDiscrepancy extends RuntimeException {
    }

    static class BoardDiscrepancy extends RuntimeException {
    }

    static class KingCanBeTaken extends RuntimeException {
    }
}
