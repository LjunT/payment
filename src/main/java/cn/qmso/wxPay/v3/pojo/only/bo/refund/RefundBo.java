/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: RefundBo
 * Author:   杨朝湖
 * Date:     2021/3/4 11:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v3.pojo.only.bo.refund;

import lombok.Data;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class RefundBo {
    /**
     * 原支付交易对应的商户订单号。
     * 示例值：1217752501201407033233368018
     */
    private String out_trade_no;
    /**
     * 商户系统内部的退款单号，商户系统内部唯一，只能是数字、大小写字母_-|*@ ，同一退款单号多次请求只退一笔。
     * 示例值：1217752501201407033233368018
     */
    private String out_refund_no;
    /**
     * 若商户传入，会在下发给用户的退款消息中体现退款原因。
     * 示例值：商品已售完
     */
    private String reason;
    /**
     * 异步接收微信支付退款结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。 如果参数中传了notify_url，则商户平台上配置的回调地址将不会生效，优先回调当前传的这个地址。
     * 示例值：https://weixin.qq.com
     */
    private String notify_url;
    /**
     * 若传递此参数则使用对应的资金账户退款，否则默认使用未结算资金退款（仅对老资金流商户适用）。
     * 枚举值：
     * AVAILABLE：可用余额账户
     * 示例值：AVAILABLE
     */
    private String funds_account;
    /**
     * 订单金额信息。
     */
    private Amount amount;
    /**
     * 指定商品退款需要传此参数，其他场景无需传递。
     */
    private List<GoodsDetail> goods_detail;


}