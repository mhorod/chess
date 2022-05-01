package app.core.game.moves;

import app.core.game.Piece;

public class PiecePick<P extends Piece> implements Move<P> {
    private final P piece;

    protected PiecePick(P piece) {
        this.piece = piece;
    }

    @Override
    public final void match(MoveMatcher<P> matcher) {
        matcher.piecePick(piece);
    }
}
