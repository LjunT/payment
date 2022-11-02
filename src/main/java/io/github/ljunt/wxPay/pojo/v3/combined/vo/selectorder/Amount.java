/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: Amount
 * Author:   杨朝湖
 * Date:     2021/3/4 14:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package io.github.ljunt.wxPay.pojo.v3.combined.vo.selectorder;

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
     * 子单金额，单位为分。
     * 示例值：100
     */
    private int total_amount;
    /**
     * 订单现金支付金额。
     * 示例值： 10
     */
    private int payer_amount;
    /**
     * 符合ISO 4217标准的三位字母代码，人民币：CNY。
     * 示例值：CNY
     */
    private String currency;
    /**
     * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY。
     * 示例值： CNY
     */
    private String payer_currency;
}