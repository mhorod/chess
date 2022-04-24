package app.pieceplayer;

import app.core.game.*;
import app.core.interactor.*;

/**
 * Player wrapper that allows moving with pieces instead moves
 * TODO: Implement methods that are not abstract
 */
public abstract class PiecePlayer<M extends Move<P>, P extends Piece> extends Player<M, P> {
}
