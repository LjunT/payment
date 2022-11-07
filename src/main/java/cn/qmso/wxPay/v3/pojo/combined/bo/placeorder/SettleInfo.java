package cn.qmso.wxPay.v3.pojo.combined.bo.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class SettleInfo {
    /**
     * 是否指定分账，枚举值
     * true：是
     * false：否
     * 示例值：true
     */
    private boolean profit_sharing;
    /**
     * SettleInfo.profit_sharing为true时，该金额才生效。
     * 注意：单笔订单最高补差金额为5000元
     * 示例值：10
     */
    private Integer subsidy_amount;
}