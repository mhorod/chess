package app.utils.pieceplayer;


import app.core.game.GameView;
import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.GameSocket;
import app.core.interactor.Player;
import app.utils.pieceplayer.controls.PlayerControls;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Creates and controls all game players at once allowing to connect pieces only once
 * <p>
 * <b>NOTE:</b> If there is a piece belonging to multiple players it will be connected only to one of them
 */
public class HotSeatPlayer<M extends Move<P>, P extends Piece> extends PiecePlayer<M, P> {

    List<Player<M, P>> players = new ArrayList<>();

    /**
     * Create new hot seat player and connect it as a spectator
     *
     * @param game view of game that will be played
     * @param socket socket that handles interactions
     */
    public HotSeatPlayer(GameView<M, P> game, GameSocket<M, P> socket) {
        for (int i = 0; i < game.getPlayerCount(); i++) {
            var player = new Player<M, P>();
            socket.connectPlayer(i, player);
            players.add(player);
        }
        socket.connectSpectator(this);
    }

    @Override
    public void connectPieces(Function<P, ? extends InteractivePiece<M, P>> newPiece) {
        for (var player : players) {
            for (var piece : player.getPieces()) {
                var interactivePiece = newPiece.apply(piece);
                interactivePiece.controls = new PlayerControls<>(piece, player);
                pieces.put(piece, interactivePiece);
            }
        }
    }
}
