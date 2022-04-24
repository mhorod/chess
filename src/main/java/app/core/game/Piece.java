package app.core.game;

/**
 * Abstract piece for any game
 */
public interface Piece {
    Field getPosition();

    boolean isAlive();
}
