package cn.qmso.wxPay.v3.pojo.only.bo.refund;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class Amount {
    /**
     * 退款金额，币种的最小单位，只能为整数，不能超过原订单支付金额。
     * 示例值：888
     */
    private int refund;
    /**
     * 原支付交易的订单总金额，币种的最小单位，只能为整数。
     * 示例值：888
     */
    private int total;
    /**
     * 符合ISO 4217标准的三位字母代码，目前只支持人民币：CNY。
     * 示例值：CNY
     */
    private String currency;
}