package app.chess;

import app.chess.moves.Castle;
import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPieceKind;
import app.chess.rules.*;
import app.chess.utils.*;
import app.core.game.Field;
import app.core.game.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

}
