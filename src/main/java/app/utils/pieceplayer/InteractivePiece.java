package app.utils.pieceplayer;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.utils.pieceplayer.controls.PieceControls;

import java.util.List;


/**
 * Interface for moving single piece, to be used with external controllers such as UI.
 * <p>
 * Override update method to implement custom behavior
 */
public abstract class InteractivePiece<M extends Move<P>, P extends Piece> {
    // Underlying controls that hide implementation details
    // This way we can still expose InteractivePiece as a class and allow to extend it
    // while having flexibility to choose between different strategies e.g. completely disallowing moves
    PieceControls<M, P> controls;

    /**
     * @return Controlled game piece
     */
    public final P getPiece() {
        return controls.getPiece();
    }

    /**
     * @return List of moves this piece can make
     */
    public final List<M> getLegalMoves() {
        return controls.getLegalMoves();
    }

    /**
     * Make a move with this piece
     */
    public final void makeMove(M move) {
        controls.makeMove(move);
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
