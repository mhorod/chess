package app.core.game.moves;

import app.core.game.Piece;

public interface Move<P extends Piece> {
    void match(MoveMatcher<P> matcher);
}
