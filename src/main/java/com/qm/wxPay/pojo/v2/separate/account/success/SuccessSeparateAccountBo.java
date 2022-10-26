/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SuccessSeparateAccountBo
 * Author:   杨朝湖
 * Date:     2021/3/10 15:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qm.wxPay.pojo.v2.separate.account.success;

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
public class SuccessSeparateAccountBo {
    /**
     * 微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 微信分配的公众账号ID
     */
    private String appid;
    /**
     * 随机字符串，不长于32位。推荐随机数生成算法
     */
    private String nonce_str;
    /**
     * 签名，详见签名生成算法
     */
    private String sign;
    /**
     * 签名类型，目前只支持HMAC-SHA256
     */
    private String sign_type;
    /**
     * 微信支付订单号
     */
    private String transaction_id;
    /**
     * 商户系统内部的分账单号，在商户系统内部唯一（单次分账、多次分账、完结分账应使用不同的商户分账单号），同一分账单号多次请求等同一次。只能是数字、大小写字母_-|*@
     */
    private String out_order_no;
    /**
     * 分账完结的原因描述
     */
    private String description;
}