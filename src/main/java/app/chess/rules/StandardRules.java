package app.chess.rules;

import app.chess.rules.ruleset.*;

import java.util.List;

/**
 * Default rules used in standard chess
 */
public class StandardRules implements Rules {
    @Override
    public List<Rule> getRules() {
        List<Rule> rules = new KingsSafetyDisabledRules().getRules();

        //We also enable king's safety
        rules.add(new YourKingCannotBeCheckedAfterYourMove());

        //Also castling is enabled here
        rules.add(new KingCantBeCheckedDuringCastling());
        rules.add(new KingAndRookCantMoveBeforeCastling());
        rules.add(new ThereCanBeNothingBetweenKingAndRookDuringCastling());
        rules.add(new NothingOnTheRoadOfTheKingIsUnderAttack());

        return rules;
    }
}
