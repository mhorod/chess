package app.utils.pieceplayer.controls;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.game.moves.PieceMove;
import app.core.game.moves.PiecePick;
import app.core.interactor.Player;
import app.utils.pieceplayer.IllegalMoveAttempt;

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
        if (move instanceof PiecePick<?>)
            player.makeMove(move);
        else if (!(move instanceof PieceMove<?>))
            throw new IllegalMoveAttempt();
        else if (!piece.equals(((PieceMove<?>) move).getPiece()))
            throw new IllegalMoveAttempt("Piece specified by the move does not match piece of this");
        else
            player.makeMove(move);
    }
}
