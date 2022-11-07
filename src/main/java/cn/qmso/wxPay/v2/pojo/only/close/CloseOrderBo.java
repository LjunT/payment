package cn.qmso.wxPay.v2.pojo.only.close;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class CloseOrderBo {
    /**
     * 微信分配的公众账号ID（企业号corpid即为此appId）
     */
    private String appid;
    /**
     * 微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
     */
    private String out_trade_no;
    /**
     * 随机字符串，不长于32位。推荐随机数生成算法
     */
    private String nonce_str;
    /**
     * 签名，详见签名生成算法
     */
    private String sign;
    /**
     * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     */
    private String sign_type;

}