package app.ui.board.state.states;

import app.core.game.Field;
import app.ui.board.Board;
import app.ui.board.Piece;
import app.ui.board.state.State;
import app.ui.utils.Position;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.stream.Stream;

public class PieceDragged<P extends Piece<?, ?>> extends State<P> {
    private final Board<?, ?> board;
    private final List<Field> legalFields;
    private P selectedPiece;
    private Field highlightedField;

    PieceDragged(Board<?, ?> board, P selectedPiece) {
        this.board = board;
        this.selectedPiece = selectedPiece;
        legalFields = Stream.concat(
                selectedPiece.logical.getLegalMoveFields().keySet().stream(),
                Stream.of(selectedPiece.logical.getPiece().getPosition())
        ).toList();
    }

    @Override
    protected void init() {
        selectedPiece.pickUp();
        for (Field f : legalFields)
            if (!f.equals(selectedPiece.logical.getPiece().getPosition()))
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
    public void onPieceDrag(P piece, MouseEvent e) {
        selectedPiece.graphical.setCenter(new Position(e.getX(), e.getY()));
        if (highlightedField != null)
            board.getGraphicalField(highlightedField).unhighlight();
        highlightedField = nearestLegal(e.getX(), e.getY());
        if (highlightedField != null)
            board.getGraphicalField(highlightedField).highlight();
    }

    @Override
    public void onPieceDeleted(P p) {
        if (p == selectedPiece) changeState(new Normal<>(board));
        else {
            var oldPiece = selectedPiece;
            selectedPiece = null;
            changeState(new PieceDragged<>(board, oldPiece));
        }
    }

    @Override
    public void onPieceDrop(P piece) {
        if (highlightedField != null && !highlightedField.equals(selectedPiece.logical.getPiece().getPosition()))
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
