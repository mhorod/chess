package app.utils.pieceplayer;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.GameSocket;
import app.core.interactor.Player;
import app.utils.pieceplayer.controls.NoControls;
import app.utils.pieceplayer.controls.PieceControls;
import app.utils.pieceplayer.controls.PlayerControls;

import java.util.function.Function;


/**
 * Wraps single player allowing controlling it with individual pieces
 */
public final class StandalonePiecePlayer<M extends Move<P>, P extends Piece> extends PieceSocket<M, P> {

    private final Player<M, P> player;

    public StandalonePiecePlayer(GameSocket<M, P> socket, int playerId) {
        player = new Player<>();
        socket.connectPlayer(playerId, player);
        socket.connectSpectator(this);
    }

    @Override
    public void connectPieces(Function<P, ? extends InteractivePiece<M, P>> newPiece) {
        super.connectPieces(newPiece);
        for (var piece : player.getAllPieces()) {
            connectPiece(piece);
        }
    }

    @Override
    protected void connectPiece(P piece) {
        var interactivePiece = newPiece.apply(piece);
        PieceControls<M, P> controls;
        if (piece.getPlayer() == player.getID())
            controls = new PlayerControls<>(piece, player);
        else
            controls = new NoControls<>(piece);
        interactivePiece.controls = controls;
        pieces.put(piece, interactivePiece);
    }
}
