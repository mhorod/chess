package app.utils.pieceplayer;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.game.moves.PieceMove;
import app.core.interactor.Player;

import java.util.ArrayList;
import java.util.List;


/**
 * Interface for moving single piece, to be used with external controllers such as UI
 */
public class InteractivePiece<M extends Move<P>, P extends Piece> {
    P piece;
    Player<M, P> player;

    /**
     * @return Controlled game piece
     */
    public final P getPiece() {
        return piece;
    }


    /**
     * Returns list of available moves. If player is null returns empty list
     */
    public final List<M> getLegalMoves() {
        if (player == null)
            return new ArrayList<>();
        else
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
            player.makeMove(move);
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
