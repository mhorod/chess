package app.ui.board.state.states;

import app.ui.board.Board;
import app.ui.board.Piece;
import app.ui.board.state.State;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Set;

public class Normal<P extends app.core.game.Piece> extends State<P> {
    private final Board<P> board;

    public Normal(Board<P> board) {
        this.board = board;
    }

    @Override
    protected void init() {
        board.selectPiece(null);
        board.setHighlightedField(null);
        board.setLegalFields(Set.of());
        board.showPiecePicker(List.of());
    }

    @Override
    public void onPieceClick(Piece<?, P> piece) {
        changeState(new PieceSelected<>(board, piece));
    }

    @Override
    public void onPieceDrag(Piece<?, P> piece, MouseEvent e) {
        changeState(new PieceDragged<>(board, piece));
    }
}
