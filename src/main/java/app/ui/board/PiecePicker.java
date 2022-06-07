package app.ui.board;

import app.core.game.Piece;
import app.ui.board.state.Behavior;
import app.ui.styles.Style;
import app.ui.utils.Position;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Function;

public class PiecePicker<P extends Piece> extends Pane {
    Function<P, GraphicalPiece<P>> supplier;
    GridPane grid;
    Style style;
    Behavior<P> behavior;
    double fieldSize;

    public PiecePicker(
            double fieldSize, Style style, Function<P, GraphicalPiece<P>> supplier, Behavior<P> behavior, List<P> pieces
    ) {
        this.behavior = behavior;
        this.style = style;
        this.supplier = supplier;
        this.fieldSize = fieldSize;
        grid = new GridPane();

        getChildren().add(grid);

        setMaxHeight(fieldSize + 25);

        setMaxWidth(0);

        setEffect(new DropShadow());

        for (int i = 0; i < pieces.size(); i++) {
            var p = pieces.get(i);
            var field = new GraphicalField(style.blackField, style.blackField, fieldSize,
                                           new Position(fieldSize * i + fieldSize / 2, fieldSize / 2));
            grid.add(field, i, 0);
            var label = new Label(String.valueOf(i + 1), style.font, style.borderBlack, style.borderText);
            grid.add(label, i, 1);
            var piece = supplier.apply(p);
            piece.setOnMouseClicked(e -> {
                behavior.onPiecePick(p);
            });
            piece.setOnMouseEntered(e -> piece.pickUp(field));
            piece.setOnMouseExited(e -> piece.putDown(field));

            var cornerRadius = fieldSize / 5;
            if (i == 0) {
                field.setBackground(new Background(
                        new BackgroundFill(style.blackField, new CornerRadii(cornerRadius, 0, 0, 0, false),
                                           Insets.EMPTY)));
                label.setBackground(new Background(
                        new BackgroundFill(style.borderBlack, new CornerRadii(0, 0, 0, cornerRadius, false),
                                           Insets.EMPTY)));
            }

            if (i == pieces.size() - 1) {
                field.setBackground(new Background(
                        new BackgroundFill(style.blackField, new CornerRadii(0, cornerRadius, 0, 0, false),
                                           Insets.EMPTY)));
                label.setBackground(new Background(
                        new BackgroundFill(style.borderBlack, new CornerRadii(0, 0, cornerRadius, 0, false),
                                           Insets.EMPTY)));
            }
            piece.putDownImmediately(field);
            setMaxWidth(getMaxWidth() + fieldSize);


            getChildren().add(piece);
        }
    }
}