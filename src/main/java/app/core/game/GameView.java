package app.core.game;

import app.core.game.moves.Move;

import java.util.List;

/**
 * Interface for retrieving game state
 * <p>
 * This is separate interface from the game because sometimes we just want to look at the game without modifying it and
 * putting everything in Game would violate Interface Segregation Principle
 */
public interface GameView<M extends Move<P>, P extends Piece> {

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
     * Returns number of player this game requires
     */
    int getPlayerCount();
}
