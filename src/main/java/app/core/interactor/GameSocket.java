package app.core.interactor;

import app.core.game.Piece;
import app.core.game.moves.Move;

/**
 * Interface of socket that players and spectators can connect to in order to play/spectate the game
 */
public interface GameSocket<M extends Move<P>, P extends Piece> {
    /**
     * Connects the player and handles the controls
     *
     * @param playerId id of connected player (in game)
     * @param player player to be connected
     */
    void connectPlayer(int playerId, Player<M, P> player);

    /**
     * Connects spectator that listens to the events
     *
     * @param spectator spectator to be connected
     */
    void connectSpectator(Spectator<M, P> spectator);
}
