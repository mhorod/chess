package app.ui.board;

import app.core.game.Field;
import app.ui.Style;
import app.ui.board.state.Behavior;
import app.ui.utils.Position;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;

public abstract class GraphicalBoard<P extends app.core.game.Piece> extends Pane {
    public final Style style;
    protected final GraphicalField[][] graphicalFields = new GraphicalField[8][8];
    double fieldSize;

    public GraphicalBoard(double fieldSize, Style style) {
        this.style = style;
        this.fieldSize = fieldSize;
        setMaxWidth(8 * fieldSize + 25);
        setMaxHeight(8 * fieldSize + 25);
        setEffect(new DropShadow());
        GridPane grid = new GridPane();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if ((x + y) % 2 == 1)
                    graphicalFields[x][y] = new GraphicalField(style.blackField, style.blackFieldCircle, fieldSize,
                            new Position(25 + x * fieldSize + fieldSize / 2, y * fieldSize + fieldSize / 2));
                else
                    graphicalFields[x][y] = new GraphicalField(style.whiteField, style.whiteFieldCircle, fieldSize,
                            new Position(25 + x * fieldSize + fieldSize / 2, y * fieldSize + fieldSize / 2));


                grid.add(graphicalFields[x][y], x + 1, y + 1);
            }
        }

        var cornerRadius = fieldSize / 5;

        graphicalFields[7][0].setBackground(new Background(new BackgroundFill(style.blackField, new CornerRadii(0, cornerRadius, 0, 0, false), Insets.EMPTY)));

        for (int y = 0; y < 8; y++) {
            Label label;
            String text = String.valueOf(y + 1);
            if (y % 2 == 1)
                label = new Label(getLeftLabel(y), style.font, style.borderBlack, style.borderText);
            else
                label = new Label(getLeftLabel(y), style.font, style.borderWhite, style.borderText);

            if (y == 0)
                label.setBackground(new Background(new BackgroundFill(style.borderBlack, new CornerRadii(cornerRadius, 0, 0, 0, false), Insets.EMPTY)));
            if (y == 7)
                label.setBackground(new Background(new BackgroundFill(style.borderWhite, new CornerRadii(0, 0, 0, cornerRadius, false), Insets.EMPTY)));

            grid.add(label, 0, y + 1);
        }

        for (int x = 0; x < 8; x++) {
            Label label;
            String text = String.valueOf((char) (7 - x + 65));
            if (x % 2 == 0)
                label = new Label(getBottomLabel(x), style.font, style.borderBlack, style.borderText);
            else
                label = new Label(getBottomLabel(x), style.font, style.borderWhite, style.borderText);

            if (x == 0)
                label.setBackground(new Background(new BackgroundFill(style.borderWhite, new CornerRadii(0, 0, 0, cornerRadius, false), Insets.EMPTY)));
            if (x == 7)
                label.setBackground(new Background(new BackgroundFill(style.borderBlack, new CornerRadii(0, 0, cornerRadius, 0, false), Insets.EMPTY)));
            grid.add(label, x + 1, 9);
        }

        getChildren().add(grid);
    }

    protected abstract String getLeftLabel(int y);

    protected abstract String getBottomLabel(int x);

    public void connectBehavior(Behavior<P> behavior) {
        for (int x = 1; x <= 8; x++) {
            for (int y = 1; y <= 8; y++) {
                final Field field = new Field(y, x);
                getGraphicalField(field).setOnMouseEntered(e -> behavior.onFieldMouseEntered(field));
                getGraphicalField(field).setOnMousePressed(e -> behavior.onFieldClick(field));
            }
        }
    }

    public void add(GraphicalPiece<P> piece) {
        getChildren().add(piece);
    }

    public abstract GraphicalField getGraphicalField(Field field);
}
