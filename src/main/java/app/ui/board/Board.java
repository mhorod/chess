package app.ui.board;

import app.core.game.Field;
import app.core.game.moves.Move;
import app.ui.board.state.Behavior;
import app.ui.board.state.Machine;
import app.ui.utils.Position;
import app.utils.pieceplayer.PiecePlayer;

import java.util.*;
import java.util.function.Supplier;

public class Board<P extends app.core.game.Piece> {
    public final Behavior<P> behavior;
    Set<Field> legalFields = Set.of();
    Map<Field, Piece<?, P>> pieces = new HashMap<>();
    Field highlightedField;
    GraphicalBoard<P> board;
    Piece<?, P> selectedPiece;

    <M extends Move<P>> Board(PiecePlayer<M, P> player, GraphicalBoard<P> board, Supplier<GraphicalPiece<P>> supplier) {
        behavior = new Machine<>(this);
        this.board = board;
        List<Piece<M, P>> pieces = new ArrayList<>();
        player.connectPieces(() -> {
            var piece = new Piece<M, P>(supplier.get(), behavior, this);
            pieces.add(piece);
            return piece.logical;
        });
        pieces.forEach((piece) -> {
            piece.logical.update();
            board.add(piece.graphical);
        });
        board.connectBehavior(behavior);
    }

    private void markAsLegal(Field field) {
        System.out.println(field);
        if (pieces.containsKey(field))
            pieces.get(field).highlight();
        else
            board.getGraphicalField(field).markAsLegal();
    }

    private void unmarkAsLegal(Field field) {
        if (pieces.containsKey(field))
            pieces.get(field).unhighlight();
        board.getGraphicalField(field).toNormal();
    }

    private void highlight(Field field) {
        board.getGraphicalField(field).highlight();
    }

    private void unhighlight(Field field) {
        board.getGraphicalField(field).unhighlight();
    }

    public void setLegalFields(Set<Field> legalFields) {
        for (var field : this.legalFields)
            if (!legalFields.contains(field)) unmarkAsLegal(field);
        for (var field : legalFields)
            if (!this.legalFields.contains(field)) markAsLegal(field);
        this.legalFields = legalFields;
    }

    public void setHighlightedField(Field field) {
        if (field == null) {
            if (highlightedField != null)
                unhighlight(highlightedField);
        } else {
            if (highlightedField != null && !field.equals(highlightedField))
                unhighlight(highlightedField);
            highlight(field);
        }
        highlightedField = field;
    }

    public void movePiece(Piece<?, P> piece, Field from, Field to) {
        pieces.remove(from);
        pieces.put(to, piece);
    }

    public void selectPiece(Piece<?, P> piece) {
        if (piece == null) {
            if (selectedPiece != null) selectedPiece.putDown();
        } else {
            if (selectedPiece != null && selectedPiece != piece) selectedPiece.putDown();
            piece.pickUp();
        }
        selectedPiece = piece;
    }

    private double distance2(Field a, Position position) {
        double deltaX = board.getGraphicalField(a).getCenter().x() - position.x();
        double deltaY = board.getGraphicalField(a).getCenter().y() - position.y();
        return deltaX * deltaX + deltaY * deltaY;
    }

    public Field getNearest(Set<Field> fields, Position position) {
        return fields.stream()
                .min((a, b) -> (int) Math.signum(distance2(a, position) - distance2(b, position)))
                .orElse(null);
    }
}
