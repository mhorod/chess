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
        if (!selectedPiece.isPickedUp())
            selectedPiece.pickUp();
        for (Field f : legalFields)
            board.getGraphicalField(f).markAsLegal();
    }

    @Override
    protected void cleanUp() {
        if (selectedPiece != null) {
            selectedPiece.putDown();
            for (Field f : legalFields)
                board.getGraphicalField(f).toNormal();
        }
        if (highlightedField != null)
            board.getGraphicalField(highlightedField).unhighlight();

    }

    @Override
    public void onMove() {
        var newLegalFields = selectedPiece.logical.getLegalMoveFields().keySet();
        for (Field f : legalFields)
            if (!newLegalFields.contains(f)) board.getGraphicalField(f).toNormal();

        var oldPiece = selectedPiece;
        selectedPiece = null;
        changeState(new PieceSelected<>(board, oldPiece));
    }

    @Override
    public void onPieceDeleted(P p) {
        if (p == selectedPiece) changeState(new Normal<>(board));
        else {
            var oldPiece = selectedPiece;
            selectedPiece = null;
            changeState(new PieceSelected<>(board, oldPiece));
        }
    }

    @Override
    public void onPieceClick(P piece) {
        if (piece != selectedPiece) {
            var field = piece.logical.getPiece().getPosition();
            if (board.getGraphicalField(field).isLegal()) {
                selectedPiece.logical.makeMove(field);
                changeState(new Normal<>(board));
            } else changeState(new PieceSelected<>(board, piece));
        } else changeState(new Normal<>(board));
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
        changeState(new PieceDragged<>(board, piece, legalFields));
    }
}
