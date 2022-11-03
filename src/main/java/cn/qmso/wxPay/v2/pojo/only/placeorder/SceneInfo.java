/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SceneInfo
 * Author:   杨朝湖
 * Date:     2021/3/10 16:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v2.pojo.only.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/10
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class SceneInfo {
    /**
     * 门店编号，由商户自定义
     */
    private String id;
    /**
     * 门店名称 ，由商户自定义
     */
    private String name;
    /**
     * 门店所在地行政区划码，详细见《最新县及县以上行政区划代码》
     */
    private String area_code;
    /**
     * 门店详细地址 ，由商户自定义
     */
    private String address;
}