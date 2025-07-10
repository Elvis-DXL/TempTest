package test.spf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaySF {

    private final Map<PayEnums, PayInterface> strategies;

    @Autowired
    public PaySF(ApplicationContext context) {
        Map<String, Object> beans = context.getBeansWithAnnotation(PayService.class);
        this.strategies = beans.values().stream().filter(PayService.class::isInstance)
                .map(PayInterface.class::cast)
                .collect(Collectors.toMap(
                        item -> item.getClass().getAnnotation(PayService.class).value(),
                        Function.identity()
                ));
    }

    public PayInterface getService(PayEnums payEnums) {
        PayInterface strategy = strategies.get(payEnums);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的支付方式: " + payEnums.name());
        }
        return strategy;
    }
}