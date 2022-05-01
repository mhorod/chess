package app.utils.pieceplayer;

import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.interactor.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


/**
 * Player wrapper that allows moving with pieces instead moves
 * TODO: Allow extending this class or add listeners to propagate game events
 */
public final class PiecePlayer<M extends Move<P>, P extends Piece> extends Player<M, P> {
    Map<P, InteractivePiece<M, P>> pieces = new HashMap<>();

    @Override
    public void update(int player, M move, List<P> changedPieces) {
        for (var piece : changedPieces) {
            pieces.get(piece).update(move);
            pieces.get(piece).update();
        }
    }

    /**
     * Moves a piece with given move
     */
    void movePiece(M move) {
        var updatedPieces = makeMove(move);
        for (var piece : updatedPieces) {
            pieces.get(piece).update(move);
            pieces.get(piece).update();
        }
    }

    /**
     * Connects all game pieces to supplied interactive pieces
     *
     * @param pieceSupplier supplier of interactive pieces
     */
    public void connectPieces(
            Supplier<? extends InteractivePiece<M, P>> pieceSupplier
    ) {
        for (var piece : getAllPieces()) {
            var interactivePiece = pieceSupplier.get();
            interactivePiece.player = this;
            interactivePiece.piece = piece;
            pieces.put(piece, interactivePiece);
        }
    }
}
