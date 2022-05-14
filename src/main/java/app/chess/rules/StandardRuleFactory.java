package app.chess.rules;

import app.chess.rules.ruleset.*;

import java.util.*;

public class StandardRuleFactory implements RuleFactory{
    @Override
    public List<Rule> getRules() {
        List<Rule> rules = new ArrayList<>();

        rules.add(new RoadCannotBeObstructed());


        //Pawns
        rules.add(new PawnGoingForwardCantTakePieces());
        rules.add(new PawnGoingSidewaysNeedsToTakeSomething());

        return rules;
    }
}
