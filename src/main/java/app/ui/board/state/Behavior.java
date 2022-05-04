package app.ui.board.state;

import app.core.game.Field;
import app.ui.board.Piece;
import javafx.scene.input.MouseEvent;

public interface Behavior<P extends app.core.game.Piece> {
    default void onPieceClick(Piece<?, P> p) {
    }

    default void onFieldClick(Field f) {
    }

    default void onFieldMouseEntered(Field f) {
    }

    default void onPieceDrag(Piece<?, P> p, MouseEvent e) {
    }

    default void onPieceDrop(Piece<?, P> p, MouseEvent e) {
    }

    default void onPieceDeleted(Piece<?, P> p) {
    }

    default void onMove() {

    }
}
