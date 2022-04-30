package app.utils.pieceplayer;

import app.core.game.*;
import app.core.game.moves.*;
import app.core.interactor.*;

import java.util.*;
import java.util.function.*;


/**
 * Player wrapper that allows moving with pieces instead moves
 * TODO: Allow extending this class or add listeners to propagate game events
 */
public final class PiecePlayer<M extends Move<P>, P extends Piece> extends Player<M, P> {
    Map<P, InteractivePiece<M, P>> pieces = new HashMap<>();

    @Override
    public void update(int player, M move) {
        for (var piece : pieces.values()) {
            piece.update(move);
            piece.update();
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
