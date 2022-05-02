package app.utils.pieceplayer;


import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.InteractiveGame;
import app.core.interactor.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Creates and controls all game players at once allowing to connect pieces only once
 * <p>
 * <b>NOTE:</b> If there is a piece belonging to multiple players it will be connected to one of them
 */
public class HotSeatPlayer<M extends Move<P>, P extends Piece> extends PiecePlayer<M, P> {

    List<Player<M, P>> players = new ArrayList<>();

    public HotSeatPlayer(InteractiveGame<M, P, ?> game) {
        for (int i = 0; i < game.getPlayerCount(); i++) {
            var player = new Player<M, P>();
            game.connectPlayer(i, player);
            players.add(player);
        }
        game.connectSpectator(this);
    }

    @Override
    public void connectPieces(Supplier<? extends InteractivePiece<M, P>> pieceSupplier) {
        for (var player : players) {
            for (var piece : player.getPieces()) {
                var interactivePiece = pieceSupplier.get();
                interactivePiece.player = player;
                interactivePiece.piece = piece;
                pieces.put(piece, interactivePiece);
            }
        }
    }
}
