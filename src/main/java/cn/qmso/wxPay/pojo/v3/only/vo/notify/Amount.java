/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: Amount
 * Author:   杨朝湖
 * Date:     2021/3/4 12:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.pojo.v3.only.vo.notify;

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
     * 用户支付金额，单位为分。
     * 示例值：100
     */
    private int payer_total;
    /**
     * CNY：人民币，境内商户号仅支持人民币。
     * 示例值：CNY
     */
    private String currency;
    /**
     * 用户支付币种
     * 示例值：CNY
     */
    private String payer_currency;
}