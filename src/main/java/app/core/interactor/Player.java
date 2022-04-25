package app.core.interactor;

import app.core.game.Piece;
import app.core.game.moves.Move;

/**
 * Player exposed to external controllers such as UI, abstracts out player number and input method
 */
public abstract class Player<M extends Move<P>, P extends Piece> {
}
