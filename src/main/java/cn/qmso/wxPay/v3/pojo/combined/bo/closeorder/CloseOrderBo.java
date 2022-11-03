/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: CloseOrderBo
 * Author:   杨朝湖
 * Date:     2021/3/4 14:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v3.pojo.combined.bo.closeorder;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
public class CloseOrderBo {
    /**
     * 合单发起方的appid。
     * 示例值：wxd678efh567hg6787
     */
    private String combine_appid;
    /**
     * 子单信息
     */
    private List<SubOrders> sub_orders;
}