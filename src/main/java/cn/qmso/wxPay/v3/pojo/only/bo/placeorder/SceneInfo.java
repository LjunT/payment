/**
 * Copyright (C), 2020-2020,贵州铭明网络科技有限公司
 * FileName: SceneInfo
 * Author:   杨朝湖
 * Date:     2020/12/18 9:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v3.pojo.only.bo.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2020/12/18
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class SceneInfo {
    /**
     * 用户终端IP
     */
    private String payer_client_ip;
    /**
     * 商户端设备号
     */
    private String device_id;
    /**
     * 商户门店信息
     */
    private StoreInfo store_info;
    /**
     * H5 场景信息
     */
    private H5Info h5_info;
}