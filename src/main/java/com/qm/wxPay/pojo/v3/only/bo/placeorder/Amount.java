/**
 * Copyright (C), 2020-2020,贵州铭明网络科技有限公司
 * FileName: Amount
 * Author:   杨朝湖
 * Date:     2020/12/18 9:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qm.wxPay.pojo.v3.only.bo.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈订单金额〉
 *
 * @author Gym
 * @create 2020/12/18
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class Amount {
    /**
     * 总金额
     */
    private int total;
    /**
     * 货币类型
     */
    private String currency;
}