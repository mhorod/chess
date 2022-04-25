package app.core.game;

import app.core.game.moves.*;

import java.util.*;

/**
 * Interface of any game the application can play
 */
public interface Game<M extends Move<P>, P extends Piece> {
    /**
     * Returns all pieces that belong to the player
     */
    List<P> getPieces(int player);

    /**
     * Returns all pieces in the game
     */
    List<P> getAllPieces();


    /**
     * Returns list of moves available for the player
     */
    List<M> getLegalMoves(int player);

    /**
     * Returns list of moves with given piece available for the player
     */
    List<M> getLegalMoves(int player, P piece);

    /**
     * Makes the move as the player
     */
    List<P> makeMove(int player, M move);

    /**
     * Returns number of player this game requires
     */
    int getPlayerCount();
}
