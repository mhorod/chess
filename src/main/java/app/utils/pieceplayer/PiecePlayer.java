package app.utils.pieceplayer;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.Player;

/**
 * Player wrapper that allows moving with pieces instead moves
 * TODO: Implement methods that are not abstract
 */
public abstract class PiecePlayer<M extends Move<P>, P extends Piece> extends Player<M, P> {
}
