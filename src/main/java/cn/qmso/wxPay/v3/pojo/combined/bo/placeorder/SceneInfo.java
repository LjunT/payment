package cn.qmso.wxPay.v3.pojo.combined.bo.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class SceneInfo {
    /**
     * 终端设备号（门店号或收银设备ID） 。
     * 示例值：POS1:123
     */
    private String device_id;
    /**
     * 用户的客户端IP，支持IPv4和IPv6两种格式的IP地址。
     * 格式: ip(ipv4+ipv6)
     * 示例值：14.17.22.32
     */
    private String payer_client_ip;
}