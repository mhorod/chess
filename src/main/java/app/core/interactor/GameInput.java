package app.core.interactor;

import app.core.game.Piece;
import app.core.game.moves.Move;

/**
 * Represents possible interactions with game
 * TODO: figure out methods to expose and whether this interface has to exist
 */
public interface GameInput<M extends Move<P>, P extends Piece> {
}
