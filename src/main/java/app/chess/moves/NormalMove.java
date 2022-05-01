package app.chess;

import app.chess.pieces.*;
import app.core.game.*;
import app.core.game.moves.*;

public class NormalMove extends PieceMove<ChessPiece> implements ChessMove{
    public NormalMove(ChessPiece piece, Field field) {
        super(piece, field);
    }

    public Field getField(){
        return this.field;
    }
}
