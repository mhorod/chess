package app.chess.rules;

import app.chess.ChessPiece;
import app.chess.moves.ChessMove;

import java.util.Collection;

/**
 * Validator is used to filter potential piece moves and leave only those that don't break supplied rules
 */
public interface Validator {
    /**
     * Get legal moves of the piece using supplied rules
     */
    Collection<ChessMove> getLegalMoves(ChessPiece piece, ChessPiece[][] board, Collection<Rule> rules);

    /**
     * Ger legal moves of the piece using default rules
     */
    Collection<ChessMove> getLegalMoves(ChessPiece piece, ChessPiece[][] board);

    Collection<Rule> getDefaultRules();
}
