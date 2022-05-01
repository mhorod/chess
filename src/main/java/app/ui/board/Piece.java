package app.ui.board;

import app.ui.board.state.Machine;

public class Piece {
    public final GraphicalPiece graphical;
    public final LogicalPiece logical;

    Piece(GraphicalPiece graphical, Machine stateMachine) {
        this.graphical = graphical;
        graphical.setOnMousePressed(e -> stateMachine.onPieceClick(this));
        graphical.setOnMouseDragged(e -> stateMachine.onPieceDrag(this, e));
        graphical.setOnMouseReleased(e -> stateMachine.onPieceDrop(this));

        this.logical = new LogicalPiece() {
            @Override
            void update() {

            }
        };
    }
}
