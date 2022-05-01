package app.chess;

import app.core.game.*;
import app.core.game.moves.*;

public class NormalMove extends PieceMove<ChessPiece> implements ChessMove{
    protected NormalMove(ChessPiece piece, Field field) {
        super(piece, field);
    }

    Field getField(){
        return this.field;
    }
}
