package app.chess.moves;

import app.chess.ChessPiece;
import app.core.game.Field;
import app.core.game.moves.PieceMove;

/**
 * Class used to convey information about a promotion of a piece <br>
 * Please note that it carries information about the CURRENT location of said piece.
 */
public class PiecePick extends PieceMove<ChessPiece> implements ChessMove {
    public PiecePick(ChessPiece piece, Field field) {
        super(piece, field);
    }

    @Override
    public String toString() {
        return (char) ('A' + this.getField().file() - 1) + "" + this.getField().rank() + " " + this.getPiece()
                                                                                                   .getKind();
    }

}