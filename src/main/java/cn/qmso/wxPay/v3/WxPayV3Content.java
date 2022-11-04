package cn.qmso.wxPay.v3;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 微信支付 v3 常量
 * @date Date : 2022年05月11日 17:43
 */
public class WxPayV3Content {

    /**
     * 微信请求地址前缀
     */
    public static final String URL_PRE = "https://api.mch.weixin.qq.com/";

    /**
     * 微信合单支付地址
     */
    public static final String V3_COMBINE_APP_PAY_URL = "v3/combine-transactions/app";
    public static final String V3_COMBINE_JSAPI_PAY_URL = "v3/combine-transactions/jsapi";
    public static final String V3_COMBINE_NATIVE_PAY_URL = "v3/combine-transactions/native";
    public static final String V3_COMBINE_H5_PAY_URL = "v3/combine-transactions/h5";


    /**
     * 微信支付地址
     */
    public static final String V3_JSAPI_PAY_URL = "v3/pay/transactions/jsapi";
    public static final String V3_APP_PAY_URL = "v3/pay/transactions/app";
    public static final String V3_H5_PAY_URL = "v3/pay/transactions/h5";
    public static final String V3_NATIVE_PAY_URL = "v3/pay/transactions/native";

    /**
     * 微信支付订单号查询地址
     */
    public static final String V3_QUERY_TRANSACTIONS = "/v3/pay/transactions/id/";
    public static final String V3_QUERY_OUT_TRADE_NO = "/v3/pay/transactions/out-trade-no/";

    /**
     * 关闭订单接口
     */
    public static final String V3_CLOSE_ORDER_URL = "/v3/pay/transactions/out-trade-no/%s/close";

    /**
     * 申请退款地址
     */
    public static final String V3_REFUND_URL = "/v3/refund/domestic/refunds";

    /**
     * 查询退款订单
     */
    public static final String V3_SELECT_REFUND_URL = "/v3/refund/domestic/refunds/%s";

    /**
     * 申请交易账单地址
     */
    public static final String V3_TRADEBILL_URL = "/v3/bill/tradebill";

    /**
     * 申请资金账单地址
     */
    public static final String V3_FUNDFLOWBILL_URL = "/v3/bill/fundflowbill";

    /**
     * 获取微信平台证书地址
     */
    public static final String V3_CERTIFICATES_URL = "v3/certificates";

    /**
     * 商家转账到零钱地址
     */
    public static final String V3_TRANSFER_BATCHES = "v3/transfer/batches";

    /**
     * 商家转账到零钱订单查询地址
     */
    public static final String V3_TRANSFER_SELECT = "v3/transfer/batches/batch-id/%s";

    /**
     * 微信支付成功字符串
     */
    public static final String SUCCESS = "SUCCESS";

    /**
     * 微信转账完成字符串
     */
    public static final String FINISHED = "FINISHED";

    /**
     * 微信转账失败字符串
     */
    public static final String FAIL = "FAIL";

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
