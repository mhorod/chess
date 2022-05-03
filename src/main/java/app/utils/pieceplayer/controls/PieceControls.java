package app.utils.pieceplayer.controls;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.Player;

import java.util.List;

/**
 * Controls a single piece of a single player
 */
public abstract class PieceControls<M extends Move<P>, P extends Piece> {

    protected P piece;
    protected Player<M, P> player;

    protected PieceControls(P piece, Player<M, P> player) {
        this.piece = piece;
        this.player = player;
    }

    /**
     * Return list of legal moves for the piece
     */
    public abstract List<M> getLegalMoves();

    /**
     * Make a move with controlled piece
     */
    public abstract void makeMove(M move);

    /**
     * @return Controlled game piece
     */
    public final P getPiece() {
        return piece;
    }


}
