package app.core.game;

/**
 * Abstract piece for any game
 */
public interface Piece {
    /**
     * @return position this piece is located at
     */
    Field getPosition();

    /**
     * @return true if piece is still in game, false otherwise
     */
    boolean isAlive();

    /**
     * @return id of player that can play with this piece
     */
    int getPlayer();
}
