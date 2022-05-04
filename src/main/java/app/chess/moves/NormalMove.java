package app.chess.moves;

import app.chess.pieces.AbstractChessPiece;
import app.core.game.Field;
import app.core.game.moves.PieceMove;

public class NormalMove extends PieceMove<AbstractChessPiece> implements ChessMove {
    public NormalMove(AbstractChessPiece piece, Field field) {
        super(piece, field);
    }

    public Field getField() {
        return this.field;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != NormalMove.class) {
            return false;
        }

        var converted = (NormalMove) o;

        if (converted.getField().rank() != this.getField().rank()) {
            return false;
        } else {
            return converted.getField().file() == this.getField().file() && converted.getPiece() == this.getPiece();
        }
    }

    @Override
    public String toString() {
        return this.getField().rank() + " " + this.getField().file() + " " + this.getPiece().getKind();
    }


}
