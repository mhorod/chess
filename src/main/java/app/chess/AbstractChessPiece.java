package app.chess;

import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPieceColor;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Field;
import app.core.game.Piece;

import java.util.List;

/**
 * Internal game implementation of a chess piece
 */
public abstract class AbstractChessPiece implements Piece {

    protected boolean wasMoved = false;
    protected boolean isBlack;
    protected boolean isAlive = true; //Defaults to true, who would like to create a dead chess piece anyways
    protected Field position;

    protected ChessPieceKind kind;
    protected ChessPieceColor color;

    protected AbstractChessPiece(AbstractChessPiece toCopy) {
        //Java has no trivial copy constructors, so we have to do this instead
        overwriteState(toCopy);
    }

    protected AbstractChessPiece(Field position, ChessPieceKind kind, ChessPieceColor color) {

        this.position = position;
        this.kind = kind;
        this.color = color;

        int rank = position.rank();
        int file = position.file();

        //A quick validation to check whether arguments supplied here are correct
        if (rank > 8 || rank < 1 || file > 8 || file < 1) {
            throw new IncorrectPiecePlacement();
        }
    }

    /**
     * This function effectively copies all state from another piece. <br> Please, use it with caution under acceptable
     * circumstances. <br> Passing incompatible pieces might result in an exception.
     */
    public void overwriteState(AbstractChessPiece toCopy) {
        wasMoved = toCopy.wasMoved;
        isBlack = toCopy.isBlack;
        isAlive = toCopy.isAlive;
        position = toCopy.position;
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
        return color == ChessPieceColor.WHITE ? 0 : 1;
    }

    public ChessPieceKind getKind() {
        return kind;
    }

    public ChessPieceColor getColor() {
        return isBlack ? ChessPieceColor.BLACK : ChessPieceColor.WHITE;
    }

    @Override
    public String toString() {
        return "" + getColor() + " " + getKind() + " at " + getPosition();
    }

    void kill() {
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


    /**
     * @return A list of ChessMoves that are potentially valid (i.e. the caller needs to check if king's safety is ok
     *         and whether path to the given field isn't obstructed
     */
    public abstract List<ChessMove> getPotentialMoves();

    /**
     * Wraps this piece into ChessPiece that can be publicly exposed
     *
     * @return wrapped piece
     */
    public ChessPiece wrap() {
        return new ChessPiece(this);
    }

    static class IncorrectPiecePlacement extends RuntimeException {
    }

}
