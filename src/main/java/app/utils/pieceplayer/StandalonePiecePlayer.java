package app.utils.pieceplayer;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.Player;

import java.util.function.Supplier;


/**
 * Wraps single player allowing controlling it with individual pieces
 */
public final class StandalonePiecePlayer<M extends Move<P>, P extends Piece> extends PiecePlayer<M, P> {

    private final Player<M, P> player;

    public StandalonePiecePlayer(Player<M, P> player) {
        this.player = player;
    }

    /**
     * Connects all game pieces to supplied interactive pieces
     *
     * @param pieceSupplier supplier of interactive pieces
     */
    public void connectPieces(Supplier<? extends InteractivePiece<M, P>> pieceSupplier) {
        for (var piece : player.getAllPieces()) {
            var interactivePiece = pieceSupplier.get();
            interactivePiece.player = player;
            interactivePiece.piece = piece;
            pieces.put(piece, interactivePiece);
        }
    }
}
