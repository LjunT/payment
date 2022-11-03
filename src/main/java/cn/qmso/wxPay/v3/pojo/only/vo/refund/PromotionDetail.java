/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: PromotionDetail
 * Author:   杨朝湖
 * Date:     2021/3/4 11:12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v3.pojo.only.vo.refund;

import lombok.Data;

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
public class PromotionDetail {
    /**
     * 券或者立减优惠id。
     * 示例值：109519
     */
    private String promotion_id;
    /**
     * 枚举值：
     * GLOBAL：全场代金券
     * SINGLE：单品优惠
     * 示例值：SINGLE
     */
    private String scope;
    /**
     * 枚举值：
     * COUPON：代金券，需要走结算资金的充值型代金券
     * DISCOUNT：优惠券，不走结算资金的免充值型优惠券
     * 示例值：DISCOUNT
     */
    private String type;
    /**
     * 用户享受优惠的金额（优惠券面额=微信出资金额+商家出资金额+其他出资方金额 ），单位为分。
     * 示例值：5
     */
    private int amount;
    /**
     * 优惠退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为用户支付的现金，说明详见代金券或立减优惠，单位为分。
     * 示例值：100
     */
    private int refund_amount;
    /**
     * 优惠商品发生退款时返回商品信息。
     */
    private List<GoodsDetail> goods_detail;
}