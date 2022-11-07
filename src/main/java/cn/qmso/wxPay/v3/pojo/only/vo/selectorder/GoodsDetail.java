package cn.qmso.wxPay.v3.pojo.only.vo.selectorder;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
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