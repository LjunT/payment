/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SceneInfo
 * Author:   杨朝湖
 * Date:     2021/3/4 13:37
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package io.github.ljunt.wxPay.pojo.v3.combined.bo.placeorder;

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