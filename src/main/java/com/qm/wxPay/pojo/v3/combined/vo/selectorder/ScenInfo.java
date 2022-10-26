/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: ScenInfo
 * Author:   杨朝湖
 * Date:     2021/3/4 14:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qm.wxPay.pojo.v3.combined.vo.selectorder;

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
public class ScenInfo {
    /**
     * 终端设备号（门店号或收银设备ID） 。
     * 示例值：POS1:1
     */
    private String device_id;
}