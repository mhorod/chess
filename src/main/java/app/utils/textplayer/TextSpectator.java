package app.utils.textplayer;

import app.core.game.Field;
import app.core.game.GameView;
import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.game.moves.MoveMatcher;
import app.core.interactor.GameSocket;
import app.core.interactor.Spectator;

import java.util.HashMap;
import java.util.List;

public abstract class TextSpectator<M extends Move<P>, P extends Piece> implements Spectator<M, P> {
    GameView<M, P> game;
    HashMap<P, Field> piecePositions = new HashMap<>();

    public TextSpectator(GameView<M, P> game, GameSocket<M, P> socket) {
        this.game = game;
        for (var piece : game.getAllPieces()) {
            if (piece.isAlive())
                piecePositions.put(piece, piece.getPosition());
        }
        socket.connectSpectator(this);
    }

    @Override
    public void update(int player, M move, List<P> changedPieces) {
        var text = serializeMove(move);

        for (var piece : changedPieces) {
            if (!piece.isAlive())
                piecePositions.remove(piece);
            else
                piecePositions.put(piece, piece.getPosition());
        }

        update(player, text);
    }

    protected String serializeMove(M move) {
        var matcher = new MoveMatcher<P>() {
            public String result;

            @Override
            public void pieceMove(P piece, Field field) {
                result = piecePositions.get(piece).toString() + field.toString();
            }

            @Override
            public void piecePick(P piece) {
                result = serializePieceKind(piece);
            }
        };
        move.match(matcher);
        return matcher.result;
    }

    protected abstract void update(int player, String move);

    protected abstract String serializePieceKind(P piece);
}
