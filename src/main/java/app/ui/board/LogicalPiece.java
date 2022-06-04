package app.ui.board;

import app.core.game.Field;
import app.core.game.Piece;
import app.core.game.moves.Move;
import app.core.game.moves.MoveMatcher;
import app.utils.pieceplayer.InteractivePiece;

import java.util.HashMap;
import java.util.Map;

public abstract class LogicalPiece<M extends Move<P>, P extends Piece> extends InteractivePiece<M, P> {

    Map<P, M> legalPiecePicks = new HashMap<>();

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

    public Map<P, M> getLegalPiecePicks() {
        legalPiecePicks = new HashMap<>();
        for (var move : getLegalMoves()) {
            var matcher = new MoveMatcher<P>() {
                P result;

                @Override
                public void piecePick(P piece, P pick) {
                    result = pick;
                }

            };
            move.match(matcher);
            if (matcher.result != null)
                legalPiecePicks.put(matcher.result, move);
        }
        return legalPiecePicks;
    }

    public void makeMove(Field field) {
        makeMove(getLegalMoveFields().get(field));
    }

    public void makePick(P piece) {
        makeMove(legalPiecePicks.get(piece));
    }

    public abstract void update();
}
