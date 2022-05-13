package app.chess;


import app.chess.pieces.ChessPieceColor;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Field;
import app.core.game.Piece;

/**
 * Provides all information about chess piece
 */
public final class ChessPiece implements Piece {
    AbstractChessPiece piece;

    public ChessPiece(AbstractChessPiece piece) {
        this.piece = piece;
    }

    @Override
    public Field getPosition() {
        return piece.getPosition();
    }

    @Override
    public boolean isAlive() {
        return piece.isAlive();
    }

    @Override
    public int getPlayer() {
        return piece.getPlayer();
    }

    /**
     * @return kind of the piece - pawn, rook, etc.
     */
    public ChessPieceKind getKind() {
        return piece.getKind();
    }

    /**
     * @return color of the piece - black or white
     */
    public ChessPieceColor getColor() {
        return piece.getColor();
    }

    @Override
    public String toString() {
        return "" + getColor() + " " + getKind() + " at " + getPosition();
    }

    /**
     * Unwraps stored AbstractChessPiece for internal chess usage
     *
     * @return underlying chess piece
     */
    AbstractChessPiece unwrap() {
        return piece;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        else if (other.getClass() != getClass())
            return false;
        else
            return piece == ((ChessPiece) other).piece;
    }

    @Override
    public int hashCode() {
        return piece.hashCode();
    }
}
