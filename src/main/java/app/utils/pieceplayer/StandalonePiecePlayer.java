package app.utils.pieceplayer;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.GameSocket;
import app.core.interactor.Player;
import app.utils.pieceplayer.controls.PlayerControls;

import java.util.function.Supplier;


/**
 * Wraps single player allowing controlling it with individual pieces
 */
public final class StandalonePiecePlayer<M extends Move<P>, P extends Piece> extends PiecePlayer<M, P> {

    private final Player<M, P> player;

    public StandalonePiecePlayer(GameSocket<M, P> socket, int playerId) {
        player = new Player<>();
        socket.connectPlayer(playerId, player);
        socket.connectSpectator(this);
    }

    /**
     * Connects all game pieces to supplied interactive pieces
     *
     * @param pieceSupplier supplier of interactive pieces
     */
    public void connectPieces(Supplier<? extends InteractivePiece<M, P>> pieceSupplier) {
        for (var piece : player.getAllPieces()) {
            var interactivePiece = pieceSupplier.get();
            interactivePiece.controls = new PlayerControls<>(piece, player);
            pieces.put(piece, interactivePiece);
        }
    }
}
