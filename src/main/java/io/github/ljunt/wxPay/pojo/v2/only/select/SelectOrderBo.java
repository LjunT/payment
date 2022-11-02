/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SelectOrderBo
 * Author:   杨朝湖
 * Date:     2021/3/11 10:18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package io.github.ljunt.wxPay.pojo.v2.only.select;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/11
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class SelectOrderBo {
    /**
     * 微信支付分配的公众账号ID（企业号corpid即为此appId）
     */
    private String appid;
    /**
     * 微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 微信的订单号，建议优先使用
     */
    private String transaction_id;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。 详见商户订单号
     */
    private String out_trade_no;
    /**
     * 随机字符串，不长于32位。推荐随机数生成算法
     */
    private String nonce_str;
    /**
     * 通过签名算法计算得出的签名值，详见签名生成算法
     */
    private String sign;
    /**
     * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     */
    private String sign_type;
}