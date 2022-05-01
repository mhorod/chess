package app.core.interactor;

import app.core.game.Piece;
import app.core.game.moves.Move;

import java.util.List;

/**
 * Participant that receives information about moves done by player
 */
public interface Participant<M extends Move<P>, P extends Piece> {

    /**
     * Receive information that player made a move
     */
    void update(int player, M move, List<P> changedPieces);
}
