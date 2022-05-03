package app.chess.moves;

import app.chess.pieces.*;
import app.core.game.*;
import app.core.game.moves.*;

public class Castle extends PieceMove<ChessPiece> implements ChessMove {
    static class OnlyKingCanCastle extends RuntimeException{}

    public Castle(ChessPiece piece, Field field) {
        super(piece, field);

        if(piece.getKind() != ChessPieceKind.KING){
            throw new OnlyKingCanCastle();
        }
    }

    @Override
    public Field getField() {
        return this.field;
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if (o.getClass() != Castle.class){
            return false;
        }

        var converted = (Castle) o;

        if(converted.getField().rank() != this.getField().rank()){
            return false;
        }
        else {
            return converted.getField().file() == this.getField().file() && converted.getPiece() == this.getPiece();
        }
    }
}
