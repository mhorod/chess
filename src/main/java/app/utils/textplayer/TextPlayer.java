package app.utils.textplayer;

import app.core.game.GameView;
import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.GameSocket;
import app.core.interactor.Player;

import java.util.List;
import java.util.stream.Collectors;

public abstract class TextPlayer<M extends Move<P>, P extends Piece> extends TextSpectator<M, P> {
    private final Player<M, P> player;

    public TextPlayer(GameView<M, P> game, GameSocket<M, P> socket, int playerId) {
        super(game, socket);
        player = new Player<>();
        socket.connectPlayer(playerId, player);
    }

    public void makeMove(String move) {
        for (var m : player.getLegalMoves()) {
            if (serializeMove(m).equals(move)) {
                player.makeMove(m);
                return;
            }
        }
    }

    public List<String> getLegalMoves() {
        return player.getLegalMoves().stream().map(this::serializeMove).collect(Collectors.toList());
    }
}
