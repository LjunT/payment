package cn.qmso.wxPay.v3.pojo.combined.bo.closeorder;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class SubOrders {
    /**
     * 子单发起方商户号，必须与发起方appid有绑定关系。
     * 示例值：1900000109
     */
    private String mchid;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     * 示例值：20150806125346
     */
    private String out_trade_no;
    /**
     * 备注
     */
    private String description;
}