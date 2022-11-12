package cn.qmso.wxPay;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 异常
 * @date Date : 2022年11月12日 14:14
 */
public class WxPayException extends RuntimeException{
    public WxPayException(String message){
        super(message);
    }

}
