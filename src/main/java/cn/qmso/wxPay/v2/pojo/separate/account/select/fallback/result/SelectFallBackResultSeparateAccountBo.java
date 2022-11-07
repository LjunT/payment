package cn.qmso.wxPay.v2.pojo.separate.account.select.fallback.result;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class SelectFallBackResultSeparateAccountBo {
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
     * 原发起分账请求时，微信返回的微信分账单号，与商户分账单号一一对应。
     * <p>
     * 微信分账单号与商户分账单号二选一填写
     */
    private String order_id;
    /**
     * 原发起分账请求时使用的商户系统内部的分账单号。
     * <p>
     * 微信分账单号与商户分账单号二选一填写
     */
    private String out_order_no;
    /**
     * 调用回退接口提供的商户系统内部的回退单号
     */
    private String out_return_no;
}