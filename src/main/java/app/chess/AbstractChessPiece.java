package app.chess;

import app.chess.board.StandardChessBoard;
import app.chess.moves.ChessMove;
import app.chess.pieces.ChessPieceColor;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Field;

import java.util.List;

/**
 * Internal game implementation of a chess piece
 */
public abstract class AbstractChessPiece implements ChessPiece {

    protected boolean wasMoved = false;
    protected boolean isAlive = true; //Defaults to true, who would like to create a dead chess piece anyways
    protected Field position;
    protected ChessPieceKind kind;
    protected ChessPieceColor color;

    protected AbstractChessPiece(AbstractChessPiece from) {
        //Java has no trivial copy constructors, so we have to do this instead
        overwriteState(from);
    }

    protected AbstractChessPiece(
            Field position, ChessPieceKind kind, ChessPieceColor color
    ) {
        if (!StandardChessBoard.containsField(position))
            throw new AbstractChessPiece.IncorrectPiecePlacement();

        this.position = position;
        this.kind = kind;
        this.color = color;
    }

    /**
     * @return A list of ChessMoves that are potentially valid (i.e. the caller needs to check if king's safety is ok
     *         and whether path to the given field isn't obstructed
     */
    public abstract List<ChessMove> getPotentialMoves();

    /**
     * This function effectively copies all state from another piece. <br> Please, use it with caution under acceptable
     * circumstances. <br> Passing incompatible pieces might result in an exception.
     */
    public void overwriteState(AbstractChessPiece from) {
        wasMoved = from.wasMoved;
        kind = from.kind;
        color = from.color;
        isAlive = from.isAlive;
        position = from.position;
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

    public final ChessPieceKind getKind() {
        return kind;
    }

    public final ChessPieceColor getColor() {
        return color;
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


    static class IncorrectPiecePlacement extends RuntimeException {
    }

}
