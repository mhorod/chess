package app.ui.board.boards;

import app.core.game.Field;
import app.ui.Style;
import app.ui.board.GraphicalBoard;
import app.ui.board.GraphicalField;

public class NormalBoard<P extends app.core.game.Piece> extends GraphicalBoard<P> {
    public NormalBoard(double fieldSize, Style style) {
        super(fieldSize, style);
    }

    @Override
    protected String getLeftLabel(int y) {
        return String.valueOf(8 - y);

    }

    @Override
    protected String getBottomLabel(int x) {
        return String.valueOf((char) ('A' + x));
    }

    @Override
    public GraphicalField getGraphicalField(Field field) {
        return graphicalFields[field.file() - 1][8 - field.rank()];
    }
}
