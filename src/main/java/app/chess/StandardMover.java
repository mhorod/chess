package app.chess;

import app.chess.moves.Castle;
import app.chess.moves.ChessMove;
import app.chess.moves.Promotion;
import app.chess.pieces.ChessPieceKind;
import app.chess.utils.Utils;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

import static app.chess.Chess.SIZE;

class StandardMover implements Mover {
    public ChessPiece getPieceKilledByMove(ChessMove move, ChessPiece[][] board) {
        var pieceThatsEndangered = Utils.getPieceByField(move.getField(), board);

        if (pieceThatsEndangered != null) {
            return pieceThatsEndangered;
        } else {

            if (move.getPiece().getKind() == ChessPieceKind.PAWN && move.getField().file() != move.getPiece()
                                                                                                  .getPosition()
                                                                                                  .file()) {
                //It means that the pawn had to do en passant
                return Utils.getPieceByField(new Field(move.getPiece().getPosition().rank(), move.getField().file()),
                                             board);
            } else {
                return null; //Nothing's being taken by that move
            }

        }
    }

    @Override
    public List<ChessPiece> makeMove(int player, ChessMove move, ChessPiece[][] board, StateManager manager) {
        //Note: If somehow we get here move that isn't legal, we get an undefined behaviour.


        //Now, if castling is involved, I'm going to delegate it to another function because otherwise this will be too messy
        if (move.getClass() == Castle.class) {
            //I'm sure checking it that way won't cause any issues down the line
            return castleMove((Castle) move, board, manager);
        }

        if (manager.thereIsPromotionPending()) {
            var promotion = (Promotion) move;
            var wasThere = Utils.putPieceOnBoard(promotion.getPick(), move.getField(), board);
            wasThere.unwrap().kill();
            manager.markPromotionAsDone();
            manager.switchCurrentPlayer();
            return List.of(wasThere, promotion.getPick());
        }

        resetWasMoved(board); //Because things will be overwritten

        //Given the fact that this move is legal, we can simply execute it now
        if (move.getPiece().getKind() == ChessPieceKind.PAWN && (move.getField().rank() == SIZE || move.getField()
                                                                                                       .rank() == 1)) {
            //That's funny because it will result in a pawn promotion
            manager.waitForPromotion();
        } else {
            manager.switchCurrentPlayer();
        }

        int newRank = move.getField().rank();
        int newFile = move.getField().file();

        ChessPiece attacked = getPieceKilledByMove(move, board);

        if (attacked != null) {
            attacked.unwrap().kill();
            board[attacked.getPosition().rank()][attacked.getPosition().file()] = null;
        }
        board[newRank][newFile] = move.getPiece();

        //Updating the board
        Utils.putPieceOnBoard(null, move.getPiece().getPosition(), board);

        //Now we are changing the internal state of the piece that's moving
        move.getPiece().unwrap().move(move.getField());

        //After executing the move, we can finally return a list informing what's happening on the board
        ArrayList<ChessPiece> changedList = new ArrayList<>();
        changedList.add(move.getPiece());
        if (attacked != null) {
            changedList.add(attacked);
        }

        return changedList;

    }


    private List<ChessPiece> castleMove(Castle move, ChessPiece[][] board, StateManager manager) {
        Field whereRookIs = Utils.getRookPositionBasedOnCastling(move);

        int rookRank = whereRookIs.rank();
        int rookFile = whereRookIs.file();

        //Castling can't kill anything, etc.
        //Just changing positions of 2 pieces, nothing can go wrong
        //Right?

        var king = move.getPiece();
        int kingRank = king.getPosition().rank(); //Yes, kingRank will be the same as rookRank
        //In fact, let's just have an additional assertion here

        if (kingRank != rookRank) {
            throw new Chess.BoardDiscrepancy();
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

        manager.switchCurrentPlayer();

        return List.of(move.getPiece(), rook);
    }

    private void resetWasMoved(ChessPiece[][] board) {
        var allPieces = Utils.getMatchingPieces(false, 0, board);

        for (var currentPiece : allPieces) {
            currentPiece.unwrap().resetMoved();
        }
    }

}