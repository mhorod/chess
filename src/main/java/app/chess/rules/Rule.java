package app.chess.rules;

import app.chess.*;
import app.chess.moves.*;

public interface Rule {
    boolean validate(ChessMove move, ChessPiece[][] board);
}
