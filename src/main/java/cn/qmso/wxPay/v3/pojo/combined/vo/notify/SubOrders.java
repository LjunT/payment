package cn.qmso.wxPay.v3.pojo.combined.vo.notify;

import lombok.Data;


/**
 * @author lijuntao
 */
@Data
public class SubOrders {
    /**
     * 子单发起方商户号，必须与发起方Appid有绑定关系。
     * 示例值：1900000109
     */
    private String mchid;
    /**
     * 枚举值：
     * NATIVE：扫码支付
     * JSAPI：公众号支付
     * APP：APP支付
     * MWEB：H5支付
     * 示例值： JSAPI
     */
    private String trade_type;
    /**
     * 枚举值：
     * SUCCESS：支付成功
     * REFUND：转入退款
     * NOTPAY：未支付
     * CLOSED：已关闭
     * USERPAYING：用户支付中
     * PAYERROR：支付失败(其他原因，如银行返回失败)
     * 示例值： SUCCESS
     */
    private String trade_state;
    /**
     * 银行类型，采用字符串类型的银行标识。银行标识请参考《银行类型对照表》
     * 示例值：CMC
     */
    private String bank_type;
    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
     * 示例值：深圳分店
     */
    private String attach;
    /**
     * 订单金额信息
     */
    private Amount amount;
    /**
     * 订单支付时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss:sss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss:sss表示时分秒毫秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35.120+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2015-05-20T13:29:35.120+08:00
     */
    private String success_time;
    /**
     * 微信支付订单号。
     * 示例值： 1009660380201506130728806387
     */
    private String transaction_id;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     * 特殊规则：最小字符长度为6
     * 示例值：20150806125346
     */
    private String out_trade_no;
}