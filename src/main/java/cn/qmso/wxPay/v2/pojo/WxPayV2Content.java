package cn.qmso.wxPay.v2.pojo;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 微信支付 v2 常量
 * @date Date : 2022年05月11日 17:43
 */
public class WxPayV2Content {

    /**
     * 微信请求地址前缀
     */
    private static final String URL_PRE = "https://api.mch.weixin.qq.com/";

    /**
     * 微信支付地址
     */
    public static final String V2PAY_URL = URL_PRE +  "pay/unifiedorder";

    /**
     * 微信订单查询地址
     */
    public static final String V2ORDER_QUERY_URL = URL_PRE + "pay/orderquery";

    /**
     * 微信订单关闭地址
     */
    public static final String V2ORDER_CLOSE_URL = URL_PRE + "pay/closeorder";

    /**
     * 微信订单退款地址
     */
    public static final String V2ORDER_REFUND_URL = URL_PRE + "secapi/pay/refund";

    /**
     * 微信订单退款地址
     */
    public static final String V2QUERY_REFUND_ORDER_URL = URL_PRE + "pay/refundquery";

    /**
     * 企业付款到零钱接口地址
     */
    public static final String V2TRANSFER_URL = URL_PRE + "/mmpaymkttransfers/promotion/transfers";

    /**
     * 查询企业付款到零钱接口地址
     */
    public static final String V2QUERY_TRANSFER_URL = URL_PRE + "/mmpaymkttransfers/gettransferinfo";

    /**
     * 付款到银行卡接口地址
     */
    public static final String TRANSFER_BANK_URL = URL_PRE + "/mmpaysptrans/pay_bank";

    /**
     * 查询付款到银行卡接口地址
     */
    public static final String QUERY_TRANSFER_BANK_URL = URL_PRE + "mmpaysptrans/query_bank";

    /**
     * 刷新公钥地址
     */
    public static final String REFRESH_PUBLIC_KEY_URL = "https://fraud.mch.weixin.qq.com/risk/getpublickey";

    /**
     * 微信支付成功字符串
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 微信支付 返回码字符串
     */
    public static final String RETURN_CODE = "return_code";

    /**
     * 微信交易类型
     */
    public enum TRADE_TYPE {
        /**
         * app支付
         */
        APP("APP"),
        /**
         * 扫码支付
         */
        NATIVE("NATIVE"),
        /**
         * 公众号、小程序支付
         */
        JSAPI("JSAPI"),
        /**
         * h5支付
         */
        H5("MWEB");


        TRADE_TYPE(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        /**
         * 字符类型
         */
        private String tradeType;
    }



}
