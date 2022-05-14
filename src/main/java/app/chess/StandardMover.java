package app.chess;
import app.chess.moves.*;
import app.chess.pieces.*;
import app.chess.utils.*;
import app.core.game.*;

import java.util.*;

import static app.chess.Chess.SIZE;

class StandardMover implements Mover {

    @Override
    public List<ChessPiece> makeMove(int player, ChessMove move, ChessPiece[][] board, StateManager manager) {
        //Note: If somehow we get here move that isn't legal, we get an undefined behaviour.



        //Now, if castling is involved, I'm going to delegate it to another function because otherwise this will be too messy
        if (move.getClass() == Castle.class) {
            //I'm sure checking it that way won't cause any issues down the line
            return castleMove((Castle) move, board);
        }

        if (manager.thereIsPromotionPending()) {
            Utils.putPieceOnBoard(move.getPiece(),move.getField(),board);
            manager.markPromotionAsDone();
            manager.switchCurrentPlayer();
            return Collections.singletonList(move.getPiece());
        }

        resetWasMoved(board); //Because things will be overwritten

        //Given the fact that this move is legal, we can simply execute it now
        if (move.getPiece().getKind() == ChessPieceKind.PAWN && (move.getField().rank() == SIZE || move.getField()
                                                                                                       .rank() == 1)) {
            //That's funny because it will result in a pawn promotion
            manager.waitForPromotion();
        }
        else{
            manager.switchCurrentPlayer();
        }

        int newRank = move.getField().rank();
        int newFile = move.getField().file();

        int oldRank = move.getPiece().getPosition().rank();
        int oldFile = move.getPiece().getPosition().file();

        var wasHereBefore = board[newRank][newFile];

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
                wasHereBefore.unwrap().kill();
                board[oldRank][newFile] = null;
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


    private List<ChessPiece> castleMove(Castle move, ChessPiece[][] board) {
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

        return List.of(move.getPiece(), rook);
    }

    private void resetWasMoved(ChessPiece[][] board) {
        var allPieces = Utils.getMatchingPieces(false, 0, board);

        for (var currentPiece : allPieces) {
            currentPiece.unwrap().resetMoved();
        }
    }

}