package app.ui.board;

import app.chess.pieces.ChessPiece;
import app.core.game.moves.Move;
import app.ui.ImageManager;
import app.ui.board.state.Machine;

public class Piece<M extends Move<P>, P extends ChessPiece> {
    public final GraphicalPiece graphical;
    public final LogicalPiece<M, P> logical;

    Piece(GraphicalPiece graphical, Machine<app.ui.board.Piece<M, P>> stateMachine, Board<M, P> board) {
        this.graphical = graphical;
        graphical.setOnMousePressed(e -> stateMachine.onPieceClick(this));
        graphical.setOnMouseDragged(e -> stateMachine.onPieceDrag(this, e));
        graphical.setOnMouseReleased(e -> stateMachine.onPieceDrop(this));

        this.logical = new LogicalPiece<>() {
            @Override
            public void update() {
                graphical.putDown(board.getGraphicalField(getPiece().getPosition()));
                graphical.setImage(ImageManager.getPieceImage(getPiece().getKind()));
                graphical.setColor(getPiece().getPlayer() == 0 ? board.style.whitePiece : board.style.blackPiece);
                if (!getPiece().isAlive()) graphical.setVisible(false);
            }
        };
    }
}
