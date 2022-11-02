/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: CombinedPayBo
 * Author:   杨朝湖
 * Date:     2021/3/4 13:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.pojo.v3.combined.bo.placeorder;

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
public class CombinedPayBo {
    /**
     * 合单发起方的appid。
     * 示例值：wxd678efh567hg6787
     */
    private String combine_appid;
    /**
     * 合单发起方商户号。
     * 示例值：1900000109
     */
    private String combine_mchid;
    /**
     * 合单支付总订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一 。
     * 示例值：P20150806125346
     */
    private String combine_out_trade_no;
    /**
     * 支付场景信息描述
     */
    private SceneInfo scene_info;
    /**
     * 最多支持子单条数：50
     */
    private List<SubOrders> sub_orders;
    /**
     * 支付者信息
     */
    private CombinePayerInfo combine_payer_info;
    /**
     * 订单生成时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2019-12-31T15:59:59+08:00
     */
    private String time_start;
    /**
     * 订单失效时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2019-12-31T15:59:59+08:00
     */
    private String time_expire;
    /**
     * 接收微信支付异步通知回调地址，通知url必须为直接可访问的URL，不能携带参数。
     * 格式: URL
     * 示例值：https://yourapp.com/notify
     */
    private String notify_url;
}