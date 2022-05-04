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
    boolean pickedUp = false;

    Piece(GraphicalPiece<P> graphical, Behavior<P> behavior, Board<P> board) {
        this.graphical = graphical;
        this.board = board;
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

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void putDown() {
        pickedUp = false;
        var position = logical.getPiece().getPosition();
        graphical.putDown(board.board.getGraphicalField(position));
        board.movePiece(this, previousPosition, position);
        previousPosition = position;
    }

    public void pickUp() {
        pickedUp = true;
        graphical.pickUp(board.board.getGraphicalField(logical.getPiece().getPosition()));
    }

    public void highlight() {
        graphical.highlight(Color.web("#38a7d6"));
    }

    public void unhighlight() {
        graphical.unhighlight();
    }
}
