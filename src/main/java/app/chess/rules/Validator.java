package app.chess.rules;

import app.chess.*;
import app.chess.moves.*;

import java.util.*;

public interface Validator {
    public List<ChessMove> getLegalMoves(ChessPiece piece, ChessPiece[][] board, List<Rule> rules);
}
