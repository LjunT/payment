/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: Amount
 * Author:   杨朝湖
 * Date:     2021/3/4 12:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v3.pojo.only.vo.refundnotify;

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
     * 订单总金额，单位为分，只能为整数，详见支付金额
     * 示例值：999
     */
    private int total;
    /**
     * 退款金额，币种的最小单位，只能为整数，不能超过原订单支付金额，如果有使用券，后台会按比例退。
     * 示例值：999
     */
    private int refund;
    /**
     * 用户实际支付金额，单位为分，只能为整数，详见支付金额
     * 示例值：999
     */
    private int payer_total;
    /**
     * 退款给用户的金额，不包含所有优惠券金额
     * 示例值：999
     */
    private int payer_refund;
}