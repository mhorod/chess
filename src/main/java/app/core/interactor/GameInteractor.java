package app.core.interactor;

import app.core.game.*;

/**
 * Wraps game allowing players to interact with each other and notifies observers with game events
 * TODO: Implement interaction logic
 */
public class GameInteractor<M extends Move<P>, P extends Piece, G extends Game<M, P>> implements GameInput<M, P> {
}
