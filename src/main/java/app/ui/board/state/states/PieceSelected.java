package app.ui.board.state.states;

import app.core.game.Field;
import app.ui.board.Board;
import app.ui.board.Piece;
import app.ui.board.state.State;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class PieceSelected<P extends Piece<?, ?>> extends State<P> {
    private final List<Field> legalFields;
    private final Board<?, ?> board;
    private P selectedPiece;
    private Field highlightedField;

    PieceSelected(Board<?, ?> board, P selectedPiece) {
        this.board = board;
        this.selectedPiece = selectedPiece;
        legalFields = selectedPiece.logical.getLegalMoveFields().keySet().stream().toList();
    }

    @Override
    protected void init() {
        selectedPiece.graphical.pickUp(board.getGraphicalField(selectedPiece.logical.getPiece().getPosition()));
        for (Field f : legalFields)
            board.getGraphicalField(f).markAsLegal();
    }

    @Override
    protected void cleanUp() {
        if (selectedPiece != null) selectedPiece.putDown();
        if (highlightedField != null)
            board.getGraphicalField(highlightedField).unhighlight();
        for (Field f : legalFields)
            board.getGraphicalField(f).toNormal();
    }


    @Override
    public void onPieceClick(P piece) {
        if (piece != selectedPiece)
            changeState(new PieceSelected<>(board, piece));
        else
            changeState(new Normal<>(board));
    }

    @Override
    public void onFieldClick(Field field) {
        var graphicalField = board.getGraphicalField(field);
        if (graphicalField.isLegal()) selectedPiece.logical.makeMove(field);
        changeState(new Normal<>(board));
    }

    @Override
    public void onFieldMouseEntered(Field field) {
        if (highlightedField != null)
            board.getGraphicalField(highlightedField).unhighlight();

        var graphicalField = board.getGraphicalField(field);
        if (graphicalField.isLegal()) {
            graphicalField.highlight();
            highlightedField = field;
        }
    }

    @Override
    public void onPieceDrag(P piece, MouseEvent e) {
        selectedPiece = null;
        changeState(new PieceDragged<>(board, piece));
    }
}
