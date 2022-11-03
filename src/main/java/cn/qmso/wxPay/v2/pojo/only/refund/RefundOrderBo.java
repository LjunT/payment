/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: RefundOrderBo
 * Author:   杨朝湖
 * Date:     2021/3/11 10:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v2.pojo.only.refund;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/11
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class RefundOrderBo {
    /**
     * 微信生成的订单号，在支付通知中有返回
     */
    private String transaction_id;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     * transaction_id、out_trade_no二选一，如果同时存在优先级：transaction_id> out_trade_no
     */
    private String out_trade_no;
    /**
     * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     */
    private String out_refund_no;
    /**
     * 订单总金额，单位为分，只能为整数，详见支付金额
     */
    private String total_fee;
    /**
     * 退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
     */
    private String refund_fee;
    /**
     * 退款货币类型，需与支付一致，或者不填。符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    private String refund_fee_type;
    /**
     * 若商户传入，会在下发给用户的退款消息中体现退款原因
     * <p>
     * 注意：若订单退款金额≤1元，且属于部分退款，则不会在退款消息中体现退款原因
     */
    private String refund_desc;
    /**
     * 仅针对老资金流商户使用
     * <p>
     * REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
     * <p>
     * REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
     */
    private String refund_account;
    /**
     * 异步接收微信支付退款结果通知的回调地址，通知URL必须为外网可访问的url，不允许带参数
     * <p>
     * 如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效。
     */
    private String notify_url;
}