package app.core.interactor;

import app.core.game.Piece;
import app.core.game.moves.Move;

import java.util.List;

/**
 * Spectator that receives information about moves done by players
 */
public interface Spectator<M extends Move<P>, P extends Piece> {

    /**
     * Receive information that player made a move
     */
    void update(int player, M move, List<P> changedPieces);
}
