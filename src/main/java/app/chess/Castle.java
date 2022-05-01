package app.chess;

import app.core.game.*;
import app.core.game.moves.*;

public class Castle extends PieceMove<ChessPiece> implements ChessMove{
    static class OnlyKingCanCastle extends RuntimeException{}

    protected Castle(ChessPiece piece, Field field) {
        super(piece, field);

        if(piece.getKind() != ChessPieceKind.KING){
            throw new OnlyKingCanCastle();
        }
    }

    @Override
    public Field getField() {
        return this.field;
    }
}
