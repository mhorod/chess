package app.chess.rules;

import app.chess.AbstractChessPiece;
import app.chess.moves.ChessMove;

import java.util.Collection;
import java.util.List;

public class StandardValidator implements Validator {
    private final Rules factory = new StandardRules();
    private final List<Rule> defaultRules = factory.getRules();

    @Override
    public List<ChessMove> getLegalMoves(
            AbstractChessPiece piece, AbstractChessPiece[][] board, Collection<Rule> rules
    ) {
        return piece.getPotentialMoves()
                    .stream()
                    .filter(move -> rules.stream().allMatch(rule -> rule.validate(move, board)))
                    .toList();
    }

    @Override
    public List<ChessMove> getLegalMoves(AbstractChessPiece piece, AbstractChessPiece[][] board) {
        return getLegalMoves(piece, board, getDefaultRules());
    }

    @Override
    public List<Rule> getDefaultRules() {
        return defaultRules;
    }
}
