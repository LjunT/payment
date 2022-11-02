/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: Amount
 * Author:   杨朝湖
 * Date:     2021/3/4 11:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.pojo.v3.only.bo.refund;

import lombok.Data;
import lombok.experimental.Accessors;

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
public class Amount {
    /**
     * 退款金额，币种的最小单位，只能为整数，不能超过原订单支付金额。
     * 示例值：888
     */
    private int refund;
    /**
     * 原支付交易的订单总金额，币种的最小单位，只能为整数。
     * 示例值：888
     */
    private int total;
    /**
     * 符合ISO 4217标准的三位字母代码，目前只支持人民币：CNY。
     * 示例值：CNY
     */
    private String currency;
}