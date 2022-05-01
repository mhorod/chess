package app.ui.board.state.states;

import app.ui.board.Board;
import app.ui.board.Piece;
import app.ui.board.state.State;
import javafx.scene.input.MouseEvent;

public class Normal<P extends Piece<?, ?>> extends State<P> {
    private final Board<?, ?> board;

    public Normal(Board<?, ?> board) {
        this.board = board;
    }

    @Override
    public void onPieceClick(P piece) {
        changeState(new PieceSelected<>(board, piece));
    }

    @Override
    public void onPieceDrag(P piece, MouseEvent e) {
        changeState(new PieceDragged<>(board, piece));
    }
}
