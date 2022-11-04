/**
 * Copyright (C), 2020-2020,贵州铭明网络科技有限公司
 * FileName: WxPayRequestBo
 * Author:   杨朝湖
 * Date:     2020/12/18 8:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v3.pojo.only.bo.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈微信支付请求的参数实体〉
 *
 * @author Gym
 * @create 2020/12/18
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class WxPayRequestBo {
    /**
     * 商品描述
     */
    private String description;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 交易结束时间
     */
    private String time_expire;
    /**
     * 附加数据
     */
    private String attach;
    /**
     * 通知地址
     */
    private String notify_url;
    /**
     * 订单优惠标记
     */
    private String goods_tag;
    /**
     * 结算信息
     */
    private SettleInfo settle_info;
    /**
     * 订单金额
     */
    private Amount amount;
    /**
     * 支付者
     */
    private Payer payer;
    /**
     * 优惠功能
     */
    private Detail detail;
    /**
     * 场景信息
     */
    private SceneInfo scene_info;
}