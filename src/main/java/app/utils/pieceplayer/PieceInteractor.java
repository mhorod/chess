package app.utils.pieceplayer;

import app.core.game.Piece;
import app.core.game.moves.Move;

/**
 * Interface for moving single piece, to be used with external controllers such as UI
 * TODO: Implement logic that doesn't need to be abstract
 */
public abstract class PieceInteractor<M extends Move<P>, P extends Piece> {
}
