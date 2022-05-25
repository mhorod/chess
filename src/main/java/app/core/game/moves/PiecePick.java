package app.core.game.moves;

import app.core.game.Piece;

public class PiecePick<P extends Piece> implements Move<P> {
    protected final P piece;

    protected PiecePick(P piece) {
        this.piece = piece;
    }

    public final P getPiece() {
        return piece;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o.getClass() != getClass()) {
            return false;
        } else {
            var converted = (PiecePick<?>) o;
            return piece.equals(converted.piece);
        }
    }

    @Override
    public int hashCode() {
        return piece.hashCode();
    }

    @Override
    public final void match(MoveMatcher<P> matcher) {
        matcher.piecePick(piece);
    }
}
