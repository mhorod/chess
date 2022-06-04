package app.core.game.moves;

import app.core.game.Field;
import app.core.game.Piece;

/**
 * Emulates enum-like behavior on moves.
 * <p>
 * Every Move has `match` method that accepts a matcher and calls appropriate method.
 * <p>
 * Without the matcher we have to use `instanceof` and downcast to desired type.
 *
 * @param <P>
 */
public interface MoveMatcher<P extends Piece> {
    default void pieceMove(P piece, Field field) {
    }

    default void piecePick(P piece) {
    }
}


