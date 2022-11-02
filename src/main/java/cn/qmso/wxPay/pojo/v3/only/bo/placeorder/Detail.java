/**
 * Copyright (C), 2020-2020,贵州铭明网络科技有限公司
 * FileName: Detail
 * Author:   杨朝湖
 * Date:     2020/12/18 9:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.pojo.v3.only.bo.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈优惠功能〉
 *
 * @author Gym
 * @create 2020/12/18
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class Detail {
    /**
     * 订单原价
     */
    private int cost_price;
    /**
     * 商品小票ID
     */
    private String invoice_id;
    /**
     * 单品列表
     */
    private List<GoodsDetail> goods_detail;
}