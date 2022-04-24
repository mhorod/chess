package app.core.game.moves;

import app.core.game.Field;
import app.core.game.Piece;

public interface MoveMatcher<P extends Piece> {
    default void pieceMove(P piece, Field field) {
    }

    default void piecePick(P piece) {
    }
}


