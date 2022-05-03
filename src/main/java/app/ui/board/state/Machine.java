package app.ui.board.state;

import app.core.game.Field;
import app.ui.board.Board;
import app.ui.board.Piece;
import app.ui.board.state.states.Normal;
import javafx.scene.input.MouseEvent;

public class Machine<P extends Piece<?, ?>> implements Behavior<P> {
    State<P> current;

    public Machine(Board<?, ?> board) {
        current = new Normal<>(board);
        current.loadMachine(this);
        current.init();
    }

    @Override
    public void onPieceClick(P p) {
        current.onPieceClick(p);
    }

    @Override
    public void onFieldClick(Field f) {
        current.onFieldClick(f);
    }

    @Override
    public void onFieldMouseEntered(Field f) {
        current.onFieldMouseEntered(f);
    }

    @Override
    public void onPieceDrag(P p, MouseEvent e) {
        current.onPieceDrag(p, e);
    }

    @Override
    public void onPieceDrop(P p) {
        current.onPieceDrop(p);
    }

    @Override
    public void onPieceDeleted(P p) {
        current.onPieceDeleted(p);
    }

    void changeState(State<P> newState) {
        current.cleanUp();
        current = newState;
        newState.loadMachine(this);
        newState.init();
    }
}
