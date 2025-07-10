package test.spf;

@PayService(PayEnums.WECHAT)
public class WechatPay implements PayInterface {
    @Override
    public boolean doPay(Integer num) {
        return true;
    }
}