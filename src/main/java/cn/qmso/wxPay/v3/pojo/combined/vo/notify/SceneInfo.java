package cn.qmso.wxPay.v3.pojo.combined.vo.notify;

import lombok.Data;


/**
 * @author lijuntao
 */
@Data
public class SceneInfo {
    /**
     * 终端设备号（门店号或收银设备ID）。
     * 特殊规则：长度最小7个字节
     * 示例值：POS1:1
     */
    private String device_id;
}