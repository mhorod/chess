package app.chess.moves;

import app.chess.ChessPiece;
import app.core.game.Field;
import app.core.game.moves.Move;

/**
 * Move specific for chess
 */
public interface ChessMove extends Move<ChessPiece> {
    /**
     * @return The field on which the piece should be after making the move
     */
    Field getField();

    /**
     * @return The instance of piece that is supposed to make a move
     */
    ChessPiece getPiece();

}
