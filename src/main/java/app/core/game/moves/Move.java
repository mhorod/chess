package app.core.game.moves;

import app.core.game.Piece;

/**
 * Phantom interface to ensure type correctness in higher abstraction layers.
 *
 * @param <P>
 */
public interface Move<P extends Piece> {
    void match(MoveMatcher<P> matcher);
}
