package app.ui.board.boards;

import app.core.game.Field;
import app.ui.board.GraphicalBoard;
import app.ui.board.GraphicalField;
import app.ui.styles.Style;

public class InvertedBoard<P extends app.core.game.Piece> extends GraphicalBoard<P> {
    public InvertedBoard(double fieldSize, Style style) {
        super(fieldSize, style);
    }

    @Override
    protected String getLeftLabel(int y) {
        return String.valueOf(y + 1);

    }

    @Override
    protected String getBottomLabel(int x) {
        return String.valueOf((char) ('A' + 7 - x));
    }

    @Override
    public GraphicalField getGraphicalField(Field field) {
        return graphicalFields[8 - field.file()][field.rank() - 1];
    }
}

