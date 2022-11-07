package cn.qmso.wxPay.v3.pojo.only.vo.notify;

import lombok.Data;


/**
 * @author lijuntao
 */
@Data
public class SceneInfo {
    /**
     * 终端设备号（门店号或收银设备ID）。
     * 示例值：013467007045764
     */
    private String device_id;
}