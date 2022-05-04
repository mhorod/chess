package app.chess.moves;

import app.chess.pieces.AbstractChessPiece;
import app.core.game.Field;
import app.core.game.moves.Move;

public interface ChessMove extends Move<AbstractChessPiece> {
    /**
     * @return The field on which the piece should be after making the move
     */
    Field getField();

    /**
     * @return The instance of piece that is supposed to make a move
     */
    AbstractChessPiece getPiece();

}
