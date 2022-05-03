package app.ui.board.state;

import app.core.game.Field;
import app.ui.board.Piece;
import javafx.scene.input.MouseEvent;

public interface Behavior<P extends Piece<?, ?>> {
    default void onPieceClick(P p) {
    }

    default void onFieldClick(Field f) {
    }

    default void onFieldMouseEntered(Field f) {
    }

    default void onPieceDrag(P p, MouseEvent e) {
    }

    default void onPieceDrop(P p) {
    }

    default void onPieceDeleted(P p) {
    }
}
