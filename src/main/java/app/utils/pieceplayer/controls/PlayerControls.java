package app.utils.pieceplayer.controls;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.Player;

import java.util.List;

/**
 * Controls that allow moving pieces
 */
public class PlayerControls<M extends Move<P>, P extends Piece> extends PieceControls<M, P> {

    public PlayerControls(P piece, Player<M, P> player) {
        super(piece, player);
    }

    @Override
    public List<M> getLegalMoves() {
        return player.getLegalMoves(piece);
    }

    @Override
    public void makeMove(M move) {
        player.makeMove(move);
    }
}
