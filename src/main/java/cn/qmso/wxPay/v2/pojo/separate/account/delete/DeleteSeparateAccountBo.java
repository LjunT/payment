package cn.qmso.wxPay.v2.pojo.separate.account.delete;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class DeleteSeparateAccountBo {
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
}