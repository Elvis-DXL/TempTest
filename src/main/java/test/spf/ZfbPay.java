package test.spf;

@PayService(PayEnums.ZFB)
public class ZfbPay implements PayInterface {
    @Override
    public boolean doPay(Integer num) {
        return true;
    }
}