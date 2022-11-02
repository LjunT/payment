/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: Amount
 * Author:   杨朝湖
 * Date:     2021/3/4 11:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package io.github.ljunt.wxPay.pojo.v3.only.vo.refund;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/4
 * @since 1.0.0
 */
@Data
public class Amount {
    /**
     * 订单总金额，单位为分。
     * 示例值：100
     */
    private int total;
    /**
     * 退款标价金额，单位为分，可以做部分退款。
     * 示例值：100
     */
    private int refund;
    /**
     * 现金支付金额，单位为分，只能为整数。
     * 示例值：90
     */
    private int payer_total;
    /**
     * 退款给用户的金额，不包含所有优惠券金额。
     * 示例值：90
     */
    private int payer_refund;
    /**
     * 去掉非充值代金券退款金额后的退款金额，单位为分，退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额。
     * 示例值：100
     */
    private int settlement_refund;
    /**
     * 应结订单金额=订单金额-免充值代金券金额，应结订单金额<=订单金额，单位为分。
     * 示例值：100
     */
    private int settlement_total;
    /**
     * 优惠退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金，说明详见代金券或立减优惠，单位为分。
     * 示例值：10
     */
    private int discount_refund;
    /**
     * 符合ISO 4217标准的三位字母代码，目前只支持人民币：CNY。
     * 示例值：CNY
     */
    private String currency;
}