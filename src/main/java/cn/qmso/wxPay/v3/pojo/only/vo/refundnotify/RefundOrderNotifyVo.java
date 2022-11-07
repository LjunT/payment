package cn.qmso.wxPay.v3.pojo.only.vo.refundnotify;

import lombok.Data;


/**
 * @author lijuntao
 */
@Data
public class RefundOrderNotifyVo {
    /**
     * 直连商户的商户号，由微信支付生成并下发。
     * 示例值：1900000100
     */
    private String mchid;
    /**
     * 返回的商户订单号
     * 示例值： 1217752501201407033233368018
     */
    private String out_trade_no;
    /**
     * 微信支付订单号
     * 示例值： 1217752501201407033233368018
     */
    private String transaction_id;
    /**
     * 商户退款单号
     * 示例值： 1217752501201407033233368018
     */
    private String out_refund_no;
    /**
     * 微信退款单号
     * 示例值： 1217752501201407033233368018
     */
    private String refund_id;
    /**
     * 退款状态，枚举值：
     * SUCCESS：退款成功
     * CLOSE：退款关闭
     * ABNORMAL：退款异常，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往【服务商平台—>交易中心】，手动处理此笔退款
     * 示例值：SUCCESS
     */
    private String refund_status;
    /**
     * 1、退款成功时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日13点29分35秒。
     * 2、当退款状态为退款成功时返回此参数。
     * 示例值：2018-06-08T10:34:56+08:00
     */
    private String success_time;
    /**
     * 取当前退款单的退款入账方。
     * 1、退回银行卡：{银行名称}{卡类型}{卡尾号}
     * 2、退回支付用户零钱: 支付用户零钱
     * 3、退还商户: 商户基本账户、商户结算银行账户
     * 4、退回支付用户零钱通：支付用户零钱通
     * 示例值：招商银行信用卡0403
     */
    private String user_received_account;
    /**
     * 金额信息
     */
    private Amount amount;
}