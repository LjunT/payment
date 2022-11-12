package cn.qmso.wxPay.v2.pojo.only.refund.select;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class SelectRefundOrderBo {
    /**
     * 微信订单号查询的优先级是： refund_id > out_refund_no > transaction_id > out_trade_no
     */
    private String transaction_id;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    private String out_trade_no;
    /**
     * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     */
    private String out_refund_no;
    /**
     * 微信生成的退款单号，在申请退款接口有返回
     */
    private String refund_id;
    /**
     * 偏移量，当部分退款次数超过10次时可使用，表示返回的查询结果从这个偏移量开始取记录
     */
    private String offset;
}