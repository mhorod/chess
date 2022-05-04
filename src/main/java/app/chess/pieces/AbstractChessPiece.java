package app.chess.pieces;

import app.chess.moves.ChessMove;
import app.core.game.Field;

import java.util.List;

/**
 * Internal game implementation of a chess piece
 */
public abstract class AbstractChessPiece implements ChessPiece {

    boolean wasMoved = false;
    boolean isBlack;
    boolean isAlive = true; //Defaults to true, who would like to create a dead chess piece anyways
    Field position;

    protected AbstractChessPiece(Field position, boolean isBlack) {

        this.position = position;

        this.isBlack = isBlack;

        int rank = position.rank();
        int file = position.file();

        if (rank > 8 || rank < 1 || file > 8 || file < 1) { //A quick validation to check whether arguments supplied here are correct
            throw new IncorrectPiecePlacement();
        }

    }

    @Override
    public Field getPosition() {
        return position;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public int getPlayer() {
        return isBlack ? 1 : 0;
    }

    public boolean wasMoved() {
        return wasMoved;
    }

    public void kill() {
        isAlive = false;
    }

    public void move(Field newPosition) {
        position = newPosition;
        wasMoved = true;
    }

    public void resetMoved() {
        wasMoved = false;
    }

    public boolean enPassantable() {
        //Pawns should override this method
        return false;
    }

    public boolean canParticipateInCastling() {
        //King and Rook should override this method
        return false;
    }

    public abstract ChessPieceKind getKind();

    public ChessPieceColor getColor() {
        return isBlack ? ChessPieceColor.BLACK : ChessPieceColor.WHITE;
    }

    /**
     * @return A list of ChessMoves that are potentially valid (i.e. the caller needs to check if king's safety is ok
     *         and whether path to the given field isn't obstructed
     */
    public abstract List<ChessMove> getPotentialMoves();

    @Override
    public String toString() {
        return "" + getColor() + " " + getKind() + " at " + getPosition();
    }

    static class IncorrectPiecePlacement extends RuntimeException {
    }
}
