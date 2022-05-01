package app.ui.board.state.states;

import app.core.game.Field;
import app.ui.board.Board;
import app.ui.board.Piece;
import app.ui.board.state.State;
import app.ui.utils.Position;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class PieceDragged<P extends Piece<?, ?>> extends State<P> {
    private final Board<?, ?> board;
    private final P selectedPiece;
    private final List<Field> legalFields;
    private Field highlightedField;

    PieceDragged(Board<?, ?> board, P selectedPiece) {
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
        selectedPiece.graphical.putDown(board.getGraphicalField(selectedPiece.logical.getPiece().getPosition()));
        if (highlightedField != null)
            board.getGraphicalField(highlightedField).unhighlight();
        for (Field f : legalFields)
            board.getGraphicalField(f).toNormal();
    }

    @Override
    public void onPieceDrag(P piece, MouseEvent e) {
        selectedPiece.graphical.setCenter(new Position(e.getX(), e.getY()));
        if (highlightedField != null)
            board.getGraphicalField(highlightedField).unhighlight();
        highlightedField = nearestLegal(e.getX(), e.getY());
        if (highlightedField != null)
            board.getGraphicalField(highlightedField).highlight();
    }

    @Override
    public void onPieceDrop(P piece) {
        if (highlightedField != null)
            piece.logical.makeMove(highlightedField);
        changeState(new Normal<>(board));
    }

    private double distance2(Field a, double x, double y) {
        double deltaX = board.getGraphicalField(a).getCenter().x() - x;
        double deltaY = board.getGraphicalField(a).getCenter().y() - y;
        return deltaX * deltaX + deltaY * deltaY;
    }

    private Field nearestLegal(double realX, double realY) {
        return legalFields.stream()
                .min((a, b) -> (int) Math.signum(distance2(a, realX, realY) - distance2(b, realX, realY)))
                .orElse(null);
    }
}
