/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SceneInfo
 * Author:   杨朝湖
 * Date:     2021/3/3 15:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v3.pojo.only.vo.selectorder;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/3
 * @since 1.0.0
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