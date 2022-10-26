/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: NotifyVo
 * Author:   杨朝湖
 * Date:     2021/3/4 12:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qm.wxPay.pojo.v3.only.vo.notify;

import lombok.Data;

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
public class NotifyVo {
    /**
     * 直连商户申请的公众号或移动应用appid。
     * 示例值：wxd678efh567hg6787
     */
    private String appid;
    /**
     * 商户的商户号，由微信支付生成并下发。
     * 示例值：1230000109
     */
    private String mchid;
    /**
     * 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一。
     * 特殊规则：最小字符长度为6
     * 示例值：1217752501201407033233368018
     */
    private String out_trade_no;
    /**
     * 微信支付系统生成的订单号。
     * 示例值：1217752501201407033233368018
     */
    private String transaction_id;
    /**
     * 交易类型，枚举值：
     * JSAPI：公众号支付
     * NATIVE：扫码支付
     * APP：APP支付
     * MICROPAY：付款码支付
     * MWEB：H5支付
     * FACEPAY：刷脸支付
     * 示例值：MICROPAY
     */
    private String trade_type;
    /**
     * 交易状态，枚举值：
     * SUCCESS：支付成功
     * REFUND：转入退款
     * NOTPAY：未支付
     * CLOSED：已关闭
     * REVOKED：已撤销（付款码支付）
     * USERPAYING：用户支付中（付款码支付）
     * PAYERROR：支付失败(其他原因，如银行返回失败)
     * 示例值：SUCCESS
     */
    private String trade_state;
    /**
     * 交易状态描述
     * 示例值：支付成功
     */
    private String trade_state_desc;
    /**
     * 银行类型，采用字符串类型的银行标识。银行标识请参考《银行类型对照表》
     * 示例值：CMC
     */
    private String bank_type;
    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用
     * 示例值：自定义数据
     */
    private String attach;
    /**
     * 支付完成时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。
     * 示例值：2018-06-08T10:34:56+08:00
     */
    private String success_time;
    /**
     * 支付者信息
     */
    private Payer payer;
    /**
     * 订单金额信息
     */
    private Amount amount;
    /**
     * 支付场景信息描述
     */
    private SceneInfo scene_info;
    /**
     * 优惠功能，享受优惠时返回该字段。
     */
    private List<PromotionDetail> promotion_detail;
}