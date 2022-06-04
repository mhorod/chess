package app.chess.moves;

import app.chess.ChessPiece;
import app.core.game.Field;
import app.core.game.moves.PieceMove;

/**
 * Standard move of piece from one field to another
 */
public class NormalMove extends PieceMove<ChessPiece> implements ChessMove {
    public NormalMove(ChessPiece piece, Field field) {
        super(piece, field);
    }

    @Override
    public String toString() {
        return (char) ('A' + this.getField().file() - 1) + "" + this.getField().rank() + " " + this.getPiece()
                                                                                                   .getKind();
    }


}
