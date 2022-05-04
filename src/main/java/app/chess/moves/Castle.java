package app.chess.moves;

import app.chess.ChessPiece;
import app.chess.pieces.ChessPieceKind;
import app.core.game.Field;
import app.core.game.moves.PieceMove;

public class Castle extends PieceMove<ChessPiece> implements ChessMove {
    public Castle(ChessPiece piece, Field field) {
        super(piece, field);

        if (piece.getKind() != ChessPieceKind.KING) {
            throw new OnlyKingCanCastle();
        }
    }

    @Override
    public Field getField() {
        return this.field;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != Castle.class) {
            return false;
        }

        var converted = (Castle) o;

        if (converted.getField().rank() != this.getField().rank()) {
            return false;
        } else {
            return converted.getField().file() == this.getField().file() && converted.getPiece() == this.getPiece();
        }
    }

    static class OnlyKingCanCastle extends RuntimeException {
    }
}
