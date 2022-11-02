/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SettleInfo
 * Author:   杨朝湖
 * Date:     2021/3/4 13:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.pojo.v3.combined.bo.placeorder;

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
public class SettleInfo {
    /**
     * 是否指定分账，枚举值
     * true：是
     * false：否
     * 示例值：true
     */
    private boolean profit_sharing;
    /**
     * SettleInfo.profit_sharing为true时，该金额才生效。
     * 注意：单笔订单最高补差金额为5000元
     * 示例值：10
     */
    private Integer subsidy_amount;
}