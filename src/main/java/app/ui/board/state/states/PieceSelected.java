package app.ui.board.state.states;

import app.core.game.Field;
import app.ui.board.Board;
import app.ui.board.Piece;
import app.ui.board.state.State;
import javafx.scene.input.MouseEvent;

import java.util.Set;

public class PieceSelected<P extends app.core.game.Piece> extends State<P> {
    private final Set<Field> legalFields;
    private final Board<P> board;
    private final Piece<?, P> selectedPiece;

    PieceSelected(Board<P> board, Piece<?, P> selectedPiece) {
        this.board = board;
        this.selectedPiece = selectedPiece;
        legalFields = selectedPiece.logical.getLegalMoveFields().keySet();
    }

    @Override
    protected void init() {
        board.selectPiece(selectedPiece);
        board.setLegalFields(legalFields);
    }

    @Override
    public void onMove() {
        board.setLegalFields(selectedPiece.logical.getLegalMoveFields().keySet());
        changeState(new PieceSelected<>(board, selectedPiece));
    }

    @Override
    public void onPieceDeleted(Piece<?, P> p) {
        if (p == selectedPiece) changeState(new Normal<>(board));
        else changeState(new PieceSelected<>(board, selectedPiece));
    }

    @Override
    public void onPieceClick(Piece<?, P> piece) {
        if (piece != selectedPiece) {
            if (selectedPiece.logical.getLegalMoveFields().containsKey(piece.getPosition())) {
                selectedPiece.logical.makeMove(piece.getPosition());
                changeState(new Normal<>(board));
            } else changeState(new PieceSelected<>(board, piece));
        } else changeState(new Normal<>(board));
    }

    @Override
    public void onFieldClick(Field field) {
        if (selectedPiece.logical.getLegalMoveFields().containsKey(field))
            selectedPiece.logical.makeMove(field);
        changeState(new Normal<>(board));
    }

    @Override
    public void onFieldMouseEntered(Field field) {
        board.setHighlightedField(field);
    }

    @Override
    public void onPieceDrag(Piece<?, P> piece, MouseEvent e) {
        changeState(new PieceDragged<>(board, piece));
    }
}
