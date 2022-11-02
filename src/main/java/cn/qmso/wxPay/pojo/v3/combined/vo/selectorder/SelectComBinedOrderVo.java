/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SelectComBinedOrderVo
 * Author:   杨朝湖
 * Date:     2021/3/4 14:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.pojo.v3.combined.vo.selectorder;

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
public class SelectComBinedOrderVo {
    /**
     * 合单发起方的appid。
     * 示例值：wxd678efh567hg6787
     */
    private String combine_appid;
    /**
     * 合单发起方商户号。
     * 示例值：1900000109
     */
    private String combine_mchid;
    /**
     * 支付者
     */
    private CombinePayerInfo combine_payer_info;
    /**
     * 子单信息
     */
    private List<SubOrders> sub_orders;
    /**
     * 场景信息
     */
    private ScenInfo scene_info;
    /**
     * 合单支付总订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     * 示例值：P20150806125346
     */
    private String combine_out_trade_no;
}