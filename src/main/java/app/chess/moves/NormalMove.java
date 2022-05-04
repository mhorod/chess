package app.chess.moves;

import app.chess.pieces.*;
import app.core.game.*;
import app.core.game.moves.*;

public class NormalMove extends PieceMove<ChessPiece> implements ChessMove {
    public NormalMove(ChessPiece piece, Field field) {
        super(piece, field);
    }

    public Field getField(){
        return this.field;
    }

    @Override
    public boolean equals(Object o){
        if(o == null){
            return false;
        }
        if (o.getClass() != NormalMove.class){
            return false;
        }

        var converted = (NormalMove) o;

        if(converted.getField().rank() != this.getField().rank()){
            return false;
        }
        else {
            return converted.getField().file() == this.getField().file() && converted.getPiece() == this.getPiece();
            }
        }

    @Override
    public String toString(){
        return (char)('A' + this.getField().file() -1) + "" + this.getField().rank() + " " + this.getPiece().getKind();
    }


}
