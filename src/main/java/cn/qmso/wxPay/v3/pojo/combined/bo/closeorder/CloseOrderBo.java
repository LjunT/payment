package cn.qmso.wxPay.v3.pojo.combined.bo.closeorder;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class CloseOrderBo {
    /**
     * 合单发起方的appid。
     * 示例值：wxd678efh567hg6787
     */
    private String combine_appid;
    /**
     * 子单信息
     */
    private List<SubOrders> sub_orders;
}