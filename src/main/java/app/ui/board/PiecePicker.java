package app.ui.board;

import app.core.game.Piece;
import app.ui.Style;
import app.ui.board.state.Behavior;
import app.ui.utils.Position;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PiecePicker<P extends Piece> extends Pane {
    List<P> pieces = new ArrayList<>();
    Function<P, GraphicalPiece<P>> supplier;
    GridPane grid;
    Style style;
    Behavior<P> behavior;
    double fieldSize;

    public PiecePicker(double fieldSize, Style style, Function<P, GraphicalPiece<P>> supplier, Behavior<P> behavior) {
        this.behavior = behavior;
        this.style = style;
        this.supplier = supplier;
        this.fieldSize = fieldSize;
        grid = new GridPane();

        getChildren().add(grid);

        setMaxHeight(fieldSize + 25);
        setMaxWidth(0);
    }

    public void addPiece(P p) {
        var field = new GraphicalField(style.blackField, style.blackField, fieldSize,
                new Position(fieldSize * pieces.size() + fieldSize / 2, fieldSize / 2));
        grid.add(field, pieces.size(), 0);
        grid.add(new Label(String.valueOf(pieces.size() + 1), style.font, style.borderBlack, style.borderText), pieces.size(), 1);
        var piece = supplier.apply(p);
        piece.setOnMouseClicked(e -> {
            behavior.onPiecePick(p);
        });
        pieces.add(p);
        piece.putDownImmediately(field);
        setMaxWidth(getMaxWidth() + fieldSize);
        getChildren().add(piece);
    }
}