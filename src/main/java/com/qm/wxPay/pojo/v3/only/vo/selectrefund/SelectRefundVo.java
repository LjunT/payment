/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SelectRefundVo
 * Author:   杨朝湖
 * Date:     2021/3/4 11:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qm.wxPay.pojo.v3.only.vo.selectrefund;

import lombok.Data;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/4
 * @since 1.0.0
 */
@Data
public class SelectRefundVo {
    /**
     * 微信支付退款号。
     * 示例值：50000000382019052709732678859
     */
    private String refund_id;
    /**
     * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     * 示例值：1217752501201407033233368018
     */
    private String out_refund_no;
    /**
     * 微信支付交易订单号。
     * 示例值：1217752501201407033233368018
     */
    private String transaction_id;
    /**
     * 原支付交易对应的商户订单号。
     * 示例值：1217752501201407033233368018
     */
    private String out_trade_no;
    /**
     * 枚举值：
     * ORIGINAL：原路退款
     * BALANCE：退回到余额
     * OTHER_BALANCE：原账户异常退到其他余额账户
     * OTHER_BANKCARD：原银行卡异常退到其他银行卡
     * 示例值：ORIGINAL
     */
    private String channel;
    /**
     * 取当前退款单的退款入账方，有以下几种情况：
     * 1）退回银行卡：{银行名称}{卡类型}{卡尾号}
     * 2）退回支付用户零钱：支付用户零钱
     * 3）退还商户：商户基本账户商户结算银行账户
     * 4）退回支付用户零钱通：支付用户零钱通。
     * 示例值：招商银行信用卡0403
     */
    private String user_received_account;
    /**
     * 退款成功时间，当退款状态为退款成功时有返回。遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日13点29分35秒。
     * 示例值：2020-12-01T16:18:12+08:00
     */
    private String success_time;
    /**
     * 退款受理时间。 遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日13点29分35秒。
     * 示例值：2020-12-01T16:18:12+08:00
     */
    private String create_time;
    /**
     * 退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台-交易中心，手动处理此笔退款。
     * 枚举值：
     * SUCCESS：退款成功
     * CLOSED：退款关闭
     * PROCESSING：退款处理中
     * ABNORMAL：退款异常
     * 示例值：SUCCESS
     */
    private String status;
    /**
     * 退款所使用资金对应的资金账户类型。 枚举值：
     * UNSETTLED : 未结算资金
     * AVAILABLE : 可用余额
     * UNAVAILABLE : 不可用余额
     * OPERATION : 运营户
     * 示例值：UNSETTLED
     */
    private String funds_account;
    /**
     * 金额详细信息。
     */
    private Amount amount;
    /**
     * 优惠退款信息。
     */
    private List<PromotionDetail> promotion_detail;
}