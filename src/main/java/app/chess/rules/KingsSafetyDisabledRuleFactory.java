package app.chess.rules;

import app.chess.rules.ruleset.*;

import java.util.*;

public class KingsSafetyDisabledRuleFactory implements RuleFactory{
    @Override
    public List<Rule> getRules() {
        //Should do basically the same as the StandardRuleFactory, but without validating king safety after move
        //Please note that it still throws an exception if ENEMY king can be taken

        List<Rule> rules = new ArrayList<>();

        rules.add(new RoadCannotBeObstructed());
        rules.add(new FriendlyFireIsDisallowed());

        //Pawns
        rules.add(new PawnGoingForwardCantTakePieces());
        rules.add(new PawnGoingSidewaysNeedsToTakeSomething());



        return rules;
    }
}
