package test.spf;

import basejpa.interfaces.BaseThrowBizEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaySF implements BaseThrowBizEx {

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
        return Optional.ofNullable(strategies.get(payEnums))
                .orElseThrow(() -> bizEx("【" + payEnums.name() + "】策略未定义"));
    }
}