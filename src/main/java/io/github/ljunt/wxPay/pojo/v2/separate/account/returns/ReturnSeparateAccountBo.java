/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: ReturnSeparateAccountBo
 * Author:   杨朝湖
 * Date:     2021/3/10 15:39
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package io.github.ljunt.wxPay.pojo.v2.separate.account.returns;

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
public class ReturnSeparateAccountBo {
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
     * 原发起分账请求时，微信返回的微信分账单号，与商户分账单号一一对应。微信分账单号与商户分账单号二选一填写
     */
    private String order_id;
    /**
     * 原发起分账请求时使用的商户系统内部的分账单号。微信分账单号与商户分账单号二选一填写
     */
    private String out_order_no;
    /**
     * 商户系统内部的回退单号，商户系统内部唯一，同一回退单号多次请求等同一次，只能是数字、大小写字母_-|*@ 。
     */
    private String out_return_no;
    /**
     * 枚举值：
     * <p>
     * MERCHANT_ID：商户号（mch_id或者sub_mch_id）
     * <p>
     * 暂时只支持从商户接收方回退分账金额
     */
    private String return_account_type;
    /**
     * 回退方类型是MERCHANT_ID时，填写商户号（mch_id或者sub_mch_id）
     * <p>
     * 只能对原分账请求中成功分给商户接收方进行回退
     */
    private String return_account;
    /**
     * 需要从分账接收方回退的金额，单位为分，只能为整数，不能超过原始分账单分出给该接收方的金额
     */
    private String return_amount;
    /**
     * 分账回退的原因描述
     */
    private String description;
}