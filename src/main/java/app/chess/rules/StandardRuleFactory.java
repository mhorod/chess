package app.chess.rules;

import app.chess.rules.ruleset.*;

import java.util.*;

public class StandardRuleFactory implements RuleFactory{
    @Override
    public List<Rule> getRules() {
        List<Rule> rules = new KingsSafetyDisabledRuleFactory().getRules();

        //We also enable king's safety
        rules.add(new YourKingCannotBeCheckedAfterYourMove());

        return rules;
    }
}
