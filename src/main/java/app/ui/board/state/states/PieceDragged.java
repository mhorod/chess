package app.ui.board.state.states;

import app.core.game.Field;
import app.ui.board.Board;
import app.ui.board.Piece;
import app.ui.board.state.State;
import app.ui.utils.Position;
import javafx.scene.input.MouseEvent;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PieceDragged<P extends app.core.game.Piece> extends State<P> {
    private final Board<P> board;
    private final Set<Field> legalFields;
    private final Piece<?, P> selectedPiece;

    PieceDragged(Board<P> board, Piece<?, P> selectedPiece) {
        this.board = board;
        this.selectedPiece = selectedPiece;
        legalFields = Stream.concat(
                selectedPiece.logical.getLegalMoveFields().keySet().stream(),
                Stream.of(selectedPiece.logical.getPiece().getPosition())
        ).collect(Collectors.toSet());
    }


    @Override
    protected void init() {
        board.setLegalFields(selectedPiece.logical.getLegalMoveFields().keySet());
        board.selectPiece(selectedPiece);
    }


    @Override
    public void onPieceDrag(Piece<?, P> piece, MouseEvent e) {
        selectedPiece.graphical.setCenter(new Position(e.getX(), e.getY()));
        board.setHighlightedField(board.getNearest(legalFields, new Position(e.getX(), e.getY())));
    }

    @Override
    public void onPieceDeleted(Piece<?, P> p) {
        if (p == selectedPiece) changeState(new Normal<>(board));
        else changeState(new PieceDragged<>(board, selectedPiece));
    }

    @Override
    public void onPieceDrop(Piece<?, P> piece, MouseEvent e) {
        var field = board.getNearest(legalFields, new Position(e.getX(), e.getY()));
        if (field != piece.getPosition()) piece.logical.makeMove(field);
        changeState(new Normal<>(board));
    }
}
