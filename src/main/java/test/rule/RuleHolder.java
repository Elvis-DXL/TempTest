package test.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleHolder {

    private List<Rule> rules;

    @Autowired
    public RuleHolder(List<Rule> rules) {
        this.rules = rules;
    }

    public Rule getRule(){
        return rules.get(1);
    }
}