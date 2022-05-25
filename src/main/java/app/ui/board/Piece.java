package app.ui.board;

import app.core.game.Field;
import app.core.game.moves.Move;
import app.ui.board.state.Behavior;
import javafx.scene.paint.Color;

public class Piece<M extends Move<P>, P extends app.core.game.Piece> {
    public final GraphicalPiece<P> graphical;
    public final LogicalPiece<M, P> logical;
    private final Board<P> board;
    Field previousPosition;

    Piece(GraphicalPiece<P> graphical, Behavior<P> behavior, Board<P> board, P piece) {
        this.graphical = graphical;
        this.board = board;
        putDownImmediatly(piece);

        this.logical = new LogicalPiece<>() {
            @Override
            public void update() {
                putDown();
                graphical.update(getPiece());
                behavior.onMove();
            }
        };

        graphical.setOnMousePressed(e -> {
            if (logical.getPiece().isAlive()) behavior.onPieceClick(this);
        });

        graphical.setOnMouseDragged(e -> {
            if (logical.getPiece().isAlive()) behavior.onPieceDrag(this, e);
        });

        graphical.setOnMouseReleased(e -> {
            if (logical.getPiece().isAlive()) behavior.onPieceDrop(this, e);
        });

        graphical.setOnMouseEntered(e -> behavior.onFieldMouseEntered(logical.getPiece().getPosition()));

    }

    public Field getPosition() {
        return logical.getPiece().getPosition();
    }

    public void putDown() {
        putDown(logical.getPiece());
    }

    private void putDown(P piece) {
        var position = piece.getPosition();
        graphical.putDown(board.board.getGraphicalField(position));
        if (piece.isAlive())
            board.movePiece(this, previousPosition, position);
        else
            board.removePiece(this, position);
        previousPosition = position;
    }

    private void putDownImmediatly(P piece) {
        var position = piece.getPosition();
        graphical.putDownImmediately(board.board.getGraphicalField(position));
        board.movePiece(this, previousPosition, position);
        previousPosition = position;
    }

    public void pickUp() {
        graphical.pickUp(board.board.getGraphicalField(logical.getPiece().getPosition()));
    }

    public void highlight(Color color) {
        graphical.highlight(color);
    }

    public void unhighlight() {
        graphical.unhighlight();
    }
}
