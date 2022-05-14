package app.chess.rules;

import app.chess.*;
import app.chess.moves.*;

public interface Rule {
    boolean canBeAppliedTo(ChessMove move);
    boolean validate(ChessMove move, ChessPiece[][] board);
}
