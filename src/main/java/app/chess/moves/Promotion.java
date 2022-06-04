package app.chess.moves;

import app.chess.ChessPiece;
import app.core.game.Field;
import app.core.game.moves.PiecePick;

/**
 * Class used to convey information about a promotion of a piece
 * <br>
 * Please note that it carries information about the CURRENT location of said piece.
 */
public class Promotion extends PiecePick<ChessPiece> implements ChessMove {
    private final Field field;

    public Promotion(ChessPiece piece, ChessPiece pick, Field field) {
        super(piece, pick);
        this.field = field;
    }

    @Override
    public String toString() {
        return (char) ('A' + this.getField().file() - 1) + "" + this.getField().rank() + " " + this.getPick().getKind();
    }

    @Override
    public Field getField() {
        return field;
    }

}