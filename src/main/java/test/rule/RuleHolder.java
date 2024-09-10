package test.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RuleHolder {

    private Map<RuleEnum, Rule> ruleMap = new HashMap<>();

    @Autowired
    public RuleHolder(List<Rule> rules) {
        if (null != rules && !rules.isEmpty()) {
            rules.forEach(it -> ruleMap.put(it.ruleMark(), it));
        }
    }

    public Rule getRule(RuleEnum ruleEnum) {
        return ruleMap.get(ruleEnum);
    }
}