package app.core.game;

import app.core.game.moves.Move;

import java.util.List;

/**
 * Interface of any game the application can play
 */
public interface Game<M extends Move<P>, P extends Piece> extends GameView<M, P> {

    /**
     * Makes the move as the player
     */
    List<P> makeMove(int player, M move);
}
