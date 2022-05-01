package app.ui.board;

import app.core.game.Field;
import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.game.moves.MoveMatcher;
import app.utils.pieceplayer.InteractivePiece;

import java.util.HashMap;
import java.util.Map;

public abstract class LogicalPiece<M extends Move<P>, P extends Piece> extends InteractivePiece<M, P> {

    public Map<Field, M> getLegalMoveFields() {
        Map<Field, M> fields = new HashMap<>();
        for (var move : getLegalMoves()) {
            var matcher = new MoveMatcher<P>() {
                Field result;

                @Override
                public void pieceMove(Piece piece, Field field) {
                    result = field;
                }
            };
            move.match(matcher);
            if (matcher.result != null)
                fields.put(matcher.result, move);
        }
        return fields;
    }

    public void makeMove(Field field) {
        makeMove(getLegalMoveFields().get(field));
    }

    public abstract void update();
}
