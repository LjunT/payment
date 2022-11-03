/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: CombinePayerInfo
 * Author:   杨朝湖
 * Date:     2021/3/4 13:42
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v3.pojo.combined.bo.placeorder;

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
public class CombinePayerInfo {
    /**
     * 使用合单appid获取的对应用户openid。是用户在商户appid下的唯一标识。
     * 示例值：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     */
    private String openid;
}