package app.utils.pieceplayer;

import app.core.game.GameView;
import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.GameSocket;
import app.utils.pieceplayer.controls.NoControls;

import java.util.function.Function;


/**
 * PiecePlayer that only spectates the game i.e. connects readonly controls
 */
public final class PieceSpectator<M extends Move<P>, P extends Piece> extends PieceSocket<M, P> {

    private final GameView<M, P> game;

    public PieceSpectator(GameView<M, P> game, GameSocket<M, P> socket) {
        this.game = game;
        socket.connectSpectator(this);
    }

    /**
     * Connects all game pieces to supplied interactive pieces
     */
    @Override
    public void connectPieces(Function<P, ? extends InteractivePiece<M, P>> newPiece) {
        super.connectPieces(newPiece);
        for (var piece : game.getAllPieces()) {
            connectPiece(piece);
        }
    }

    @Override
    protected void connectPiece(P piece) {
        var interactivePiece = newPiece.apply(piece);
        interactivePiece.controls = new NoControls<>(piece);
        pieces.put(piece, interactivePiece);
    }
}
