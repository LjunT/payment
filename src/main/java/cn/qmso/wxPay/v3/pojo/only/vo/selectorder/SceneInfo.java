package cn.qmso.wxPay.v3.pojo.only.vo.selectorder;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class SceneInfo {
    /**
     * 商户端设备号（发起扣款请求的商户服务器设备号）。
     * 示例值：013467007045764
     */
    private String device_id;
}