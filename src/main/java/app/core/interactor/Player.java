package app.core.interactor;

import app.core.game.*;
import app.core.game.moves.*;

import java.util.*;

/**
 * Player exposed to external controllers such as UI, abstracts out player number and input method
 */
public abstract class Player<M extends Move<P>, P extends Piece> implements Participant<M, P> {
    int player;
    Game<M, P> game;

    /**
     * Returns all pieces that belong to this player
     */
    public final List<P> getPieces() {
        if (game == null)
            throw new UseOfUnconnectedPlayer();
        return game.getPieces(player);
    }

    /**
     * Returns all pieces in the game
     */
    public final List<P> getAllPieces() {
        if (game == null)
            throw new UseOfUnconnectedPlayer();
        return game.getAllPieces();
    }

    /**
     * Returns list of available moves
     */
    public final List<M> getLegalMoves() {
        if (game == null)
            throw new UseOfUnconnectedPlayer();
        return game.getLegalMoves(player);
    }

    /**
     * Returns list of available moves with given piece
     */
    public final List<M> getLegalMoves(P piece) {
        if (game == null)
            throw new UseOfUnconnectedPlayer();
        return game.getLegalMoves(player, piece);
    }

    /**
     * Makes a move
     */
    public final List<P> makeMove(M move) {
        if (game == null)
            throw new UseOfUnconnectedPlayer();
        return game.makeMove(player, move);
    }
}
