package app.chess.rules.ruleset;

import app.chess.moves.*;
import app.chess.rules.*;


public abstract class CastlingRules implements Rule {
    @Override
    public boolean canBeAppliedTo(ChessMove move) {
        return move.getClass() == Castle.class;
    }
}
