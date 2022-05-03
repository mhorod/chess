package app.ui.board;

import app.core.game.Field;
import app.ui.Style;
import app.ui.board.state.Behavior;
import app.ui.utils.Position;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GraphicalBoard<P extends app.core.game.Piece> extends Pane {
    double fieldSize;
    Style style;
    GraphicalField[][] graphicalFields = new GraphicalField[8][8];

    public GraphicalBoard(double fieldSize, Style style) {
        this.style = style;
        this.fieldSize = fieldSize;
        setMaxWidth(8 * fieldSize + 25);
        setMaxHeight(8 * fieldSize + 25);
        setEffect(new DropShadow());
        GridPane grid = new GridPane();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if ((x + y) % 2 == 0)
                    graphicalFields[x][y] = new GraphicalField(style.blackField, style.blackFieldCircle, fieldSize,
                            new Position(25 + x * fieldSize + fieldSize / 2, y * fieldSize + fieldSize / 2));
                else
                    graphicalFields[x][y] = new GraphicalField(style.whiteField, style.whiteFieldCircle, fieldSize,
                            new Position(25 + x * fieldSize + fieldSize / 2, y * fieldSize + fieldSize / 2));


                grid.add(graphicalFields[x][y], x + 1, y + 1);
            }
        }

        for (int y = 0; y < 8; y++) {
            Label label;
            String text = String.valueOf(y + 1);
            if (y % 2 == 1)
                label = new Label(text, style.font, style.borderBlack, style.borderText);
            else
                label = new Label(text, style.font, style.borderWhite, style.borderText);
            grid.add(label, 0, y + 1);
        }

        for (int x = 0; x < 8; x++) {
            Label label;
            String text = String.valueOf((char) (7 - x + 65));
            if (x % 2 == 0)
                label = new Label(text, style.font, style.borderBlack, style.borderText);
            else
                label = new Label(text, style.font, style.borderWhite, style.borderText);
            grid.add(label, x + 1, 9);
        }

        getChildren().add(grid);
    }

    void connectBehavior(Behavior<P> behavior) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                final Field field = new Field(y + 1, 8 - x);
                graphicalFields[x][y].setOnMouseEntered(e -> behavior.onFieldMouseEntered(field));
                graphicalFields[x][y].setOnMousePressed(e -> behavior.onFieldClick(field));
            }
        }
    }

    void add(GraphicalPiece<P> piece) {
        getChildren().add(piece);
    }

    public GraphicalField getGraphicalField(Field field) {
        return graphicalFields[8 - field.file()][field.rank() - 1];
    }
}
