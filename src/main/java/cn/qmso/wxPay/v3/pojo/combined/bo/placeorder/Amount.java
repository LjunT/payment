package cn.qmso.wxPay.v3.pojo.combined.bo.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class Amount {
    /**
     * 子单金额，单位为分
     * 境外场景下，标价金额要超过商户结算币种的最小单位金额，例如结算币种为美元，则标价金额必须大于1美分
     * 示例值：100
     */
    private int total_amount;
    /**
     * 符合ISO 4217标准的三位字母代码，人民币：CNY 。
     * 示例值：CNY
     */
    private String currency;
}