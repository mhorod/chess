package app.chess.rules;

import app.chess.AbstractChessPiece;
import app.chess.moves.ChessMove;

public interface Rule {
    boolean canBeAppliedTo(ChessMove move);

    boolean validate(ChessMove move, AbstractChessPiece[][] board);
}
