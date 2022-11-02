/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: PromotionDetail
 * Author:   杨朝湖
 * Date:     2021/3/3 15:43
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package io.github.ljunt.wxPay.pojo.v3.only.vo.selectorder;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
public class PromotionDetail {
    /**
     * 券ID
     * 示例值：109519
     */
    private String coupon_id;
    /**
     * 优惠名称
     * 示例值：单品惠-6
     */
    private String name;
    /**
     * GLOBAL：全场代金券
     * SINGLE：单品优惠
     * 示例值：GLOBAL
     */
    private String scope;
    /**
     * CASH：充值
     * NOCASH：预充值
     * 示例值：CASH
     */
    private String type;
    /**
     * 优惠券面额
     * 示例值：100
     */
    private int amount;
    /**
     * 活动ID
     * 示例值：931386
     */
    private String stock_id;
    /**
     * 微信出资，单位为分
     * 示例值：0
     */
    private int wechatpay_contribute;
    /**
     * 商户出资，单位为分
     * 示例值：0
     */
    private int merchant_contribute;
    /**
     * 其他出资，单位为分
     * 示例值：0
     */
    private int other_contribute;
    /**
     * CNY：人民币，境内商户号仅支持人民币。
     * 示例值：CNY
     */
    private String currency;
    /**
     * 单品列表信息
     */
    private List<GoodsDetail> goods_detail;


}