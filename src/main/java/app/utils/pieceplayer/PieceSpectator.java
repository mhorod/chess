package app.utils.pieceplayer;

import app.core.game.GameView;
import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.GameSocket;
import app.utils.pieceplayer.controls.NoControls;

import java.util.function.Supplier;


public final class PieceSpectator<M extends Move<P>, P extends Piece> extends PiecePlayer<M, P> {

    private final GameView<M, P> game;

    public PieceSpectator(GameView<M, P> game, GameSocket<M, P> socket) {
        this.game = game;
        socket.connectSpectator(this);
    }

    /**
     * Connects all game pieces to supplied interactive pieces
     *
     * @param pieceSupplier supplier of interactive pieces
     */
    public void connectPieces(Supplier<? extends InteractivePiece<M, P>> pieceSupplier) {
        for (var piece : game.getAllPieces()) {
            var interactivePiece = pieceSupplier.get();
            interactivePiece.controls = new NoControls<>(piece);
            pieces.put(piece, interactivePiece);
        }
    }
}
