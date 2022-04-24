package app.core.game;

import app.core.game.moves.Move;

import java.util.List;

/**
 * Interface of any game the application can play
 */
public interface Game<M extends Move<P>, P extends Piece> {
    List<P> getPieces();

    List<M> getLegalMoves(int player);

    List<M> getLegalMoves(P piece, int player);

    List<P> makeMove(int player, M move);

    int getPlayerCount();
}
