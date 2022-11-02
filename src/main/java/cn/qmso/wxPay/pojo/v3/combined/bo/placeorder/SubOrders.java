/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SubOrders
 * Author:   杨朝湖
 * Date:     2021/3/4 13:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.pojo.v3.combined.bo.placeorder;

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
public class SubOrders {
    /**
     * 子单发起方商户号，必须与发起方appid有绑定关系。
     * 示例值：1900000109
     */
    private String mchid;
    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
     * 示例值：深圳分店
     */
    private String attach;
    /**
     * 订单金额信息
     */
    private Amount amount;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     * 示例值：20150806125346
     */
    private String out_trade_no;
    /**
     * 商品简单描述。需传入应用市场上的APP名字-实际商品名称，例如：天天爱消除-游戏充值。
     * 示例值：腾讯充值中心-QQ会员充值
     */
    private String description;
    /**
     * 结算信息
     */
    private SettleInfo settle_info;

}