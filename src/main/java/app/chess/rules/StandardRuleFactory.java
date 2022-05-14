package app.chess.rules;

import java.util.*;

public class StandardRuleFactory implements RuleFactory{
    @Override
    public List<Rule> getRules() {
        List<Rule> rules = new ArrayList<>();

        rules.add(new RoadCannotBeObstructed());

        return rules;
    }
}
