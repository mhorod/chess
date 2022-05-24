package app.chess.rules;

import app.chess.rules.ruleset.FriendlyFireIsDisallowed;
import app.chess.rules.ruleset.PawnGoingForwardCantTakePieces;
import app.chess.rules.ruleset.PawnGoingSidewaysNeedsToTakeSomething;
import app.chess.rules.ruleset.RoadCannotBeObstructed;

import java.util.ArrayList;
import java.util.List;

public class KingsSafetyDisabledRules implements Rules {
    @Override
    public List<Rule> getRules() {
        //Validates only basic stuff, used by Utils to check if a field is under attack
        //Why this way? We don't want to care about pinning of pieces, etc.
        //For full rules, please see StandardRuleFactory

        List<Rule> rules = new ArrayList<>();

        rules.add(new RoadCannotBeObstructed());
        rules.add(new FriendlyFireIsDisallowed());

        //Pawns
        rules.add(new PawnGoingForwardCantTakePieces());
        rules.add(new PawnGoingSidewaysNeedsToTakeSomething());


        return rules;
    }
}
