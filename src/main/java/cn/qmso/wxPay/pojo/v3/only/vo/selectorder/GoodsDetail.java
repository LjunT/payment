/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: GoodsDetail
 * Author:   杨朝湖
 * Date:     2021/3/3 15:47
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.pojo.v3.only.vo.selectorder;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/3
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class GoodsDetail {
    /**
     * 商品编码
     * 示例值：M1006
     */
    private String goods_id;
    /**
     * 用户购买的数量
     * 示例值：1
     */
    private int quantity;
    /**
     * 商品单价，单位为分
     * 示例值：100
     */
    private int unit_price;
    /**
     * 商品优惠金额
     * 示例值：0
     */
    private int discount_amount;
    /**
     * 商品备注信息
     * 示例值：商品备注信息
     */
    private String goods_remark;
}