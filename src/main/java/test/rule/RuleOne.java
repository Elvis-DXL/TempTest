package test.rule;

import org.springframework.stereotype.Component;

@Component
public class RuleOne implements Rule {
    @Override
    public void print() {
        System.out.println("One");
    }
}
