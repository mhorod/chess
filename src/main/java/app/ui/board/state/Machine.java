package app.ui.board.state;

import app.core.game.Field;
import app.ui.board.Board;
import app.ui.board.Piece;
import app.ui.board.state.states.Normal;
import javafx.scene.input.MouseEvent;

public class Machine<P extends app.core.game.Piece> implements Behavior<P> {
    State<P> current;

    public Machine(Board<P> board) {
        current = new Normal<>(board);
        current.loadMachine(this);
        current.init();
    }

    @Override
    public void onPieceClick(Piece<?, P> p) {
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
    public void onPieceDrag(Piece<?, P> p, MouseEvent e) {
        current.onPieceDrag(p, e);
    }

    @Override
    public void onPieceDrop(Piece<?, P> p, MouseEvent e) {
        current.onPieceDrop(p, e);
    }

    @Override
    public void onPiecePick(P p) {
        current.onPiecePick(p);
    }

    @Override
    public void onPieceDeleted(Piece<?, P> p) {
        current.onPieceDeleted(p);
    }

    @Override
    public void onMove() {
        current.onMove();
    }

    void changeState(State<P> newState) {
        current.cleanUp();
        current = newState;
        newState.loadMachine(this);
        newState.init();
    }
}
