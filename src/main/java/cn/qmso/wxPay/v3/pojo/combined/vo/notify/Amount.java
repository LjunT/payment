package cn.qmso.wxPay.v3.pojo.combined.vo.notify;

import lombok.Data;


/**
 * @author lijuntao
 */
@Data
public class Amount {
    /**
     * 子单金额，单位为分。
     * 示例值：100
     */
    private int total_amount;
    /**
     * 符合ISO 4217标准的三位字母代码，人民币：CNY。
     * 示例值：CNY
     */
    private String currency;
    /**
     * 订单现金支付金额。
     * 示例值：10
     */
    private int payer_amount;
    /**
     * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY。
     * 示例值： CNY
     */
    private String payer_currency;
}