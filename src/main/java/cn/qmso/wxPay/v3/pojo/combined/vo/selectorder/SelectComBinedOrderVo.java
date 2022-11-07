package cn.qmso.wxPay.v3.pojo.combined.vo.selectorder;

import lombok.Data;

import java.util.List;


/**
 * @author lijuntao
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