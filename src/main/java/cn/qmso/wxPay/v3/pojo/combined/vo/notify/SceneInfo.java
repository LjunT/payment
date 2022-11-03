/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SceneInfo
 * Author:   杨朝湖
 * Date:     2021/3/4 14:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v3.pojo.combined.vo.notify;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/4
 * @since 1.0.0
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