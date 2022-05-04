package app.chess.pieces;

import app.chess.Chess;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractChessPiece {
    boolean wasMovedBefore = false; //Do not confuse with wasMoved of ChessPiece, this is supposed to be changed one time

    public Rook(Field position, boolean isBlack) {
        super(position, isBlack);
    }
    //This field is going to be used to check whether the rook can be involved in castling

    @Override
    public ChessPieceKind getKind() {
        return ChessPieceKind.ROOK;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        final Field currentPosition = this.getPosition();

        //This is a rook, so it can move horizontally and vertically

        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        for (int i = 1; i <= Chess.SIZE; i++) {
            Field fieldOnTheSameFile = new Field(i, currentPosition.file());
            Field fieldOnTheSameRank = new Field(currentPosition.rank(), i);

            ChessMove moveOnTheSameFile = new NormalMove(this, fieldOnTheSameFile);
            ChessMove moveOnTheSameRank = new NormalMove(this, fieldOnTheSameRank);

            potentialMoves.add(moveOnTheSameFile);
            potentialMoves.add(moveOnTheSameRank);
        }

        return potentialMoves;
    }

    @Override
    public void move(Field newPosition) {
        wasMovedBefore = true;
        position = newPosition;
    }

    @Override
    public boolean canParticipateInCastling() {
        return !wasMovedBefore;
    }
}
