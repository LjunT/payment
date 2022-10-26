/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: Receiver
 * Author:   杨朝湖
 * Date:     2021/3/10 15:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qm.wxPay.pojo.v2.separate.account.delete;

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
public class Receiver {
    /**
     * MERCHANT_ID：商户号（mch_id或者sub_mch_id）
     * PERSONAL_OPENID：个人openid
     */
    private String type;
    /**
     * 类型是MERCHANT_ID时，是商户号（mch_id或者sub_mch_id）
     * 类型是PERSONAL_OPENID时，是个人openid
     */
    private String account;
}