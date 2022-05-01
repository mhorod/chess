package app.ui.board.state;

import app.core.game.Field;
import app.ui.board.Board;
import app.ui.board.Piece;
import app.ui.board.state.states.Normal;
import javafx.scene.input.MouseEvent;

public class Machine implements Behavior {
    State current;

    public Machine(Board board) {
        current = new Normal(board);
        current.loadMachine(this);
        current.init();
    }

    @Override
    public void onPieceClick(Piece p) {
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
    public void onPieceDrag(Piece p, MouseEvent e) {
        current.onPieceDrag(p, e);
    }

    @Override
    public void onPieceDrop(Piece p) {
        current.onPieceDrop(p);
    }

    void changeState(State newState) {
        current.cleanUp();
        current = newState;
        newState.loadMachine(this);
        newState.init();
    }
}
