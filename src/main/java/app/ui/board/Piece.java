package app.ui.board;

import app.chess.ChessPiece;
import app.core.game.moves.Move;
import app.ui.ImageManager;
import app.ui.board.state.Machine;
import javafx.scene.paint.Color;

public class Piece<M extends Move<P>, P extends ChessPiece> {
    public final GraphicalPiece graphical;
    public final LogicalPiece<M, P> logical;
    private final Board<M, P> board;
    boolean pickedUp = false;

    Piece(GraphicalPiece graphical, Machine<app.ui.board.Piece<M, P>> stateMachine, Board<M, P> board) {
        this.graphical = graphical;
        this.board = board;
        this.logical = new LogicalPiece<>() {
            @Override
            public void update() {
                putDown();
                graphical.setImage(ImageManager.getPieceImage(getPiece().getKind()));
                graphical.setColor(getPiece().getPlayer() == 0 ? board.style.whitePiece : board.style.blackPiece);
                if (!getPiece().isAlive()) {
                    graphical.disappear();
                    stateMachine.onPieceDeleted(Piece.this);
                }
                stateMachine.onMove();
            }
        };

        graphical.setOnMousePressed(e -> {
            if (logical.getPiece().isAlive())
                stateMachine.onPieceClick(this);
        });

        graphical.setOnMouseDragged(e -> {
            if (logical.getPiece().isAlive())
                stateMachine.onPieceDrag(this, e);
        });

        graphical.setOnMouseReleased(e -> {
            if (logical.getPiece().isAlive())
                stateMachine.onPieceDrop(this);
        });

        graphical.setOnMouseEntered(e -> stateMachine.onFieldMouseEntered(logical.getPiece().getPosition()));

    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void putDown() {
        pickedUp = false;
        graphical.putDown(board.getGraphicalField(logical.getPiece().getPosition()));
    }

    public void pickUp() {
        pickedUp = true;
        graphical.pickUp(board.getGraphicalField(logical.getPiece().getPosition()));
    }

    public void highlight() {
        graphical.highlight(Color.web("#38a7d6"));
    }

    public void unhighlight() {
        graphical.unhighlight();
    }
}
