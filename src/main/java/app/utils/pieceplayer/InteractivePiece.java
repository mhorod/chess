package app.utils.pieceplayer;

import app.core.game.*;
import app.core.game.moves.*;

import java.util.*;

/**
 * Interface for moving single piece, to be used with external controllers such as UI
 */
public class InteractivePiece<M extends Move<P>, P extends Piece> {
    P piece;
    PiecePlayer<M, P> player;

    public final P getPiece() {
        return piece;
    }

    public final List<M> getLegalMoves() {
        return player.getLegalMoves(piece);
    }

    /**
     * Moves the piece Throws IllegalArgumentException if move is invalid for this piece
     * TODO: Figure out which move types are allowed
     */
    public final void makeMove(M move) {
        if (!(move instanceof PieceMove<?>))
            throw new IllegalMoveAttempt();
        else if (piece != ((PieceMove<?>) move).getPiece())
            throw new IllegalMoveAttempt("Piece specified by the move does not match piece of this");
        else
            player.movePiece(move);
    }

    /**
     * update is called when state of a piece changes
     *
     * @param move move that caused the change
     */
    public void update(M move) {
    }

    /**
     * update is called when state of a piece changes
     */
    public void update() {
    }

}
