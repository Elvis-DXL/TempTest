package test.spf;

@PayStrategy(PayEnums.ZFB)
public class ZfbPay implements PayInterface {
    @Override
    public boolean doPay(Integer num) {
        return true;
    }
}