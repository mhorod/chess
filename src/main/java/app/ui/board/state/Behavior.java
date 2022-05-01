package app.ui.board.state;

import app.core.game.Field;
import app.ui.board.Piece;
import javafx.scene.input.MouseEvent;

public interface Behavior {
    default void onPieceClick(Piece p) {
    }

    default void onFieldClick(Field f) {
    }

    default void onFieldMouseEntered(Field f) {
    }

    default void onPieceDrag(Piece p, MouseEvent e) {
    }

    default void onPieceDrop(Piece p) {
    }
}
