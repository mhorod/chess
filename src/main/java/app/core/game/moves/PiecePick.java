package app.core.game.moves;

import app.core.game.Piece;

/**
 * Represents transformation of piece into another one e.g. chess promotion
 *
 * @param <P> type of piece
 */
public class PiecePick<P extends Piece> implements Move<P> {
    protected final P piece;
    protected final P pick;

    protected PiecePick(P piece, P pick) {
        this.piece = piece;
        this.pick = pick;
    }

    public final P getPick() {
        return pick;
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
        matcher.piecePick(piece, pick);
    }
}
