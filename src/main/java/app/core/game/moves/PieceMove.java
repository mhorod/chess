package app.core.game.moves;

import app.core.game.Field;
import app.core.game.Piece;

public class PieceMove<P extends Piece> implements Move<P> {
    protected final P piece;
    protected final Field field;

    protected PieceMove(P piece, Field field) {
        this.piece = piece;
        this.field = field;
    }

    @Override
    public final void match(MoveMatcher<P> matcher) {
        matcher.pieceMove(piece, field);
    }

    public final P getPiece() {
        return piece;
    }

    public final Field getField() {
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o.getClass() != getClass()) {
            return false;
        } else {
            var converted = (PieceMove<?>) o;
            return piece.equals(converted.piece) && field.equals(converted.field);
        }
    }

    @Override
    public int hashCode() {
        return piece.hashCode() ^ field.hashCode();
    }
}
