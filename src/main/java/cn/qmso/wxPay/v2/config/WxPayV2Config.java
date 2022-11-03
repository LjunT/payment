package cn.qmso.wxPay.v2.config;

import cn.qmso.wxPay.base.WxPayContent;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 微信支付v2配置
 * @date Date : 2022年11月02日 17:24
 */
@Component
@ConfigurationProperties(prefix = "payment.wxpay.v2")
@Data
public class WxPayV2Config {

    /**
     * v2密钥
     */
    private String key;

    /**
     * 微信支付公众号appid
     */
    private String appid;

    /**
     * 微信支付商户号
     */
    private String mch_id;

    /**
     * 加密方式
     */
    private String sign_type = WxPayContent.SIGN_TYPE_MD5;

    /**
     * 回调地址
     */
    private String notify_url;

    /**
     * 证书地址
     */
    private String cert_path;


    @Override
    public String toString() {
        return "WxPayV2Config{" +
                "key='" + key + '\'' +
                ", appid='" + appid + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", sign_type='" + sign_type + '\'' +
                ", notify_url='" + notify_url + '\'' +
                '}';
    }
}
