package test.rule;

import org.springframework.stereotype.Component;

@Component
public class RuleTwo implements Rule {
    @Override
    public void print() {
        System.out.println("Two");
    }
}
