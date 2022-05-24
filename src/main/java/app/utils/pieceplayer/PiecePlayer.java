package app.utils.pieceplayer;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.Spectator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Player wrapper that allows moving with pieces instead moves
 */
public abstract class PiecePlayer<M extends Move<P>, P extends Piece> implements Spectator<M, P> {

    protected Map<P, InteractivePiece<M, P>> pieces = new HashMap<>();
    protected Function<P, ? extends InteractivePiece<M, P>> newPiece;

    public final void update(int player, M move, List<P> changedPieces) {
        for (var piece : changedPieces) {
            if (!pieces.containsKey(piece)) {
                connectPiece(piece);
            }

            pieces.get(piece).update(move);
            pieces.get(piece).update();
        }
    }

    /**
     * Connects underlying game pieces to supplied interactors.
     * <p>
     * Implementation should call this method and add newly created piece to the map
     *
     * @param newPiece constructor of InteractivePiece that takes P as argument
     */
    public void connectPieces(Function<P, ? extends InteractivePiece<M, P>> newPiece) {
        this.newPiece = newPiece;
    }


    /**
     * Connect a single piece and add it to the map
     */
    protected abstract void connectPiece(P piece);
}
