package cn.qmso.wxPay.v3.pojo.only.bo.selectOrder;

import lombok.Data;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 查询微信支付订单参数
 * @date Date : 2022年11月04日 9:38
 */
@Data
public class SelectOrderBo {

    /**
     * 商户号
     */
    private String mchid;

    public SelectOrderBo(String mchid) {
        this.mchid = mchid;
    }
}
