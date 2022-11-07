package cn.qmso.wxPay.v2.pojo.only.select;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class SelectOrderBo {
    /**
     * 微信的订单号，建议优先使用
     */
    private String transaction_id;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号
     */
    private String out_trade_no;
}