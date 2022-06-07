package app.chess.pieces;

import app.chess.AbstractChessPiece;
import app.chess.Chess;
import app.chess.moves.ChessMove;
import app.chess.moves.NormalMove;
import app.core.game.Field;

import java.util.ArrayList;
import java.util.List;

import static app.chess.pieces.ChessPieceKind.ROOK;

public class Rook extends AbstractChessPiece {
    boolean wasMovedBefore = false; //Do not confuse with wasMoved of ChessPiece, this is supposed to be changed one time
    //This field is going to be used to check whether the rook can be involved in castling

    public Rook(Field position, ChessPieceColor color) {
        super(position, ROOK, color);
    }

    public Rook(Rook from) {
        super(from);
        overwriteState(from);
    }

    @Override
    public void overwriteState(AbstractChessPiece from) {
        super.overwriteState(from);
        wasMovedBefore = ((Rook) from).wasMovedBefore;
    }

    @Override
    public ChessPieceKind getKind() {
        return ROOK;
    }

    @Override
    public List<ChessMove> getPotentialMoves() {
        final Field currentPosition = this.getPosition();

        //This is a rook, so it can move horizontally and vertically

        ArrayList<ChessMove> potentialMoves = new ArrayList<>();

        for (int i = 1; i <= Chess.SIZE; i++) {
            Field fieldOnTheSameFile = new Field(i, currentPosition.file());
            Field fieldOnTheSameRank = new Field(currentPosition.rank(), i);

            ChessMove moveOnTheSameFile = new NormalMove(this.wrap(), fieldOnTheSameFile);
            ChessMove moveOnTheSameRank = new NormalMove(this.wrap(), fieldOnTheSameRank);

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
