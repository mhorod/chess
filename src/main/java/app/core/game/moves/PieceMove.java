package app.core.game.moves;

import app.core.game.*;

public class PieceMove<P extends Piece> implements Move<P> {
   final P piece;
   protected final Field field;

    protected PieceMove(P piece, Field field) {
        this.piece = piece;
        this.field = field;
    }

    @Override
    public final void match(MoveMatcher<P> matcher) {
        matcher.pieceMove(piece, field);
    }

    public final P getPiece() {
        return piece;
    }
}
