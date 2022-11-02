/**
 * Copyright (C), 2020-2020,贵州铭明网络科技有限公司
 * FileName: Payer
 * Author:   杨朝湖
 * Date:     2020/12/18 9:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package io.github.ljunt.wxPay.pojo.v3.only.bo.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈支付者〉
 *
 * @author Gym
 * @create 2020/12/18
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class Payer {
    /**
     * 用户标识
     */
    private String openid;
    /**
     * 用户服务标识
     */
    private String sp_openid;
    /**
     * 用户子标识
     */
    private String sub_openid;
}