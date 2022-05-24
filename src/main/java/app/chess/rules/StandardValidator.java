package app.chess.rules;

import app.chess.ChessPiece;
import app.chess.moves.ChessMove;

import java.util.List;

public class StandardValidator implements Validator {
    private final Rules factory = new StandardRules();
    private final List<Rule> defaultRules = factory.getRules();

    @Override
    public List<ChessMove> getLegalMoves(ChessPiece piece, ChessPiece[][] board, List<Rule> rules) {
        return RulesPieceConverter.convert(piece)
                                  .getPotentialMoves()
                                  .stream()
                                  .filter(move -> rules.stream().allMatch(rule -> rule.validate(move, board)))
                                  .toList();
    }

    @Override
    public List<ChessMove> getLegalMoves(ChessPiece piece, ChessPiece[][] board) {
        return getLegalMoves(piece, board, getDefaultRules());
    }

    @Override
    public List<Rule> getDefaultRules() {
        return defaultRules;
    }
}
