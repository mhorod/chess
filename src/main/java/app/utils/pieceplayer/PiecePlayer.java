package app.utils.pieceplayer;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.Participant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Player wrapper that allows moving with pieces instead moves
 * TODO: Allow extending this class or add listeners to propagate game events
 */
public abstract class PiecePlayer<M extends Move<P>, P extends Piece> implements Participant<M, P> {

    protected Map<P, InteractivePiece<M, P>> pieces = new HashMap<>();

    public final void update(int player, M move, List<P> changedPieces) {
        for (var piece : changedPieces) {
            pieces.get(piece).update(move);
            pieces.get(piece).update();
        }
    }

    /**
     * Connects underlying game pieces to supplied interactors
     */
    public abstract void connectPieces(Supplier<? extends InteractivePiece<M, P>> pieceSupplier);
}
