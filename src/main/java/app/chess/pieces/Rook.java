package app.chess.pieces;

import app.chess.AbstractChessPiece;
import app.chess.Chess;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractChessPiece {
    boolean wasMovedBefore = false; //Do not confuse with wasMoved of ChessPiece, this is supposed to be changed one time
    //This field is going to be used to check whether the rook can be involved in castling

    public Rook(Field position, ChessPieceColor color) {
        super(position, ChessPieceKind.ROOK, color);
    }

    public Rook(Rook from) {
        super(from);
        overwriteState(from);
    }

    @Override
    public void overwriteState(AbstractChessPiece toCopy) {
        super.overwriteState(toCopy);
        wasMovedBefore = ((Rook) toCopy).wasMovedBefore;
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
