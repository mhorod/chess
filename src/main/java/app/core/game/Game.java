package app.core.game;

import app.core.game.moves.Move;

import java.util.List;


/**
 * Interface of any game the application can play.
 * <br>
 * Extends GameView with functionality that can alter state of the game.
 *
 * @param <M> type of move that is used by the implementation
 * @param <P> type of piece that is used by the implementation
 */
public interface Game<M extends Move<P>, P extends Piece> extends GameView<M, P> {

    /**
     * Makes the move as the player
     */
    List<P> makeMove(int player, M move);
}
