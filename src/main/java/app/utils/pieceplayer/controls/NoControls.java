package app.utils.pieceplayer.controls;

import app.core.game.Piece;
import app.core.game.moves.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * Controls that disallow moving
 */
public class NoControls<M extends Move<P>, P extends Piece> extends PieceControls<M, P> {

    public NoControls(P piece) {
        super(piece, null);
    }

    @Override
    public List<M> getLegalMoves() {
        return new ArrayList<>();
    }

    @Override
    public void makeMove(M move) {
        // Since there are no legal moves, any move here would be foreign and thus invalid
        throw new UnsupportedOperationException("Spectator cannot move pieces");
    }
}
