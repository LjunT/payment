/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: GoodsDetail
 * Author:   杨朝湖
 * Date:     2021/3/4 11:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package io.github.ljunt.wxPay.pojo.v3.only.bo.refund;

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
public class GoodsDetail {
    /**
     * 由半角的大小写字母、数字、中划线、下划线中的一种或几种组成。
     * 示例值：1217752501201407033233368018
     */
    private String merchant_goods_id;
    /**
     * 微信支付定义的统一商品编号（没有可不传）。
     * 示例值：1001
     */
    private String wechatpay_goods_id;
    /**
     * 商品的实际名称。
     * 示例值：iPhone6s 16G
     */
    private String goods_name;
    /**
     * 商品单价金额，单位为分。
     * 示例值：528800
     */
    private int unit_price;
    /**
     * 商品退款金额，单位为分。
     * 示例值：528800
     */
    private int refund_amount;
    /**
     * 单品的退款数量。
     * 示例值：1
     */
    private int refund_quantity;
}