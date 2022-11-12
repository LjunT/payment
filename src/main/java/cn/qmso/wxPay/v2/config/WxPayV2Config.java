package cn.qmso.wxPay.v2.config;

import cn.qmso.wxPay.base.WxPayContent;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

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
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 加密方式
     */
    private String signType = WxPayContent.SIGN_TYPE_MD5;

    /**
     * 回调地址
     */
    private String notifyUrl;

    /**
     * 证书地址
     */
    private String certPath;


    /**
     * PKCS#1格式的公钥
     */
    private String publicKey;

    /**
     * PKCS#1格式的公钥
     */
    private PublicKey pubKey;


    @Override
    public String toString() {
        return "WxPayV2Config{" +
                "key='" + key + '\'' +
                ", appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", signType='" + signType + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", certPath='" + certPath + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}
