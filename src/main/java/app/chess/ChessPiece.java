/*
 ChessPiece is an interface exposed for users outside ChessPackage e.g. for players
 Since it is exposed, it is also received in the moves and queries and virtually every part
 of chess package has to operate on those objects.

 Because of that ChessPiece is not a java interface, but a class that simply wraps internal implementation
 it provides package-private baseUnwrap method for retrieving actual piece.

  We could make it an interface and throw exception when down-casting fails, but because of
  high number of usages we decided to leave it the way it is to provide better type-safety.

  A consequence of this decision is that unwrapping is package-private (because external users don't care about implementation details)
  and thus is unavailable in sub-packages.
  There is workaround - ChessPieceConverter
*/

package app.chess;


import app.chess.pieces.ChessPieceColor;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Field;
import app.core.game.Piece;

/**
 * Information about ChessPiece that is exposed to the users of chess package
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
