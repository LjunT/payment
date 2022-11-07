package cn.qmso.wxPay.v3.pojo.combined.vo.selectorder;

import lombok.Data;


/**
 * @author lijuntao
 */
@Data
public class ScenInfo {
    /**
     * 终端设备号（门店号或收银设备ID） 。
     * 示例值：POS1:1
     */
    private String device_id;
}