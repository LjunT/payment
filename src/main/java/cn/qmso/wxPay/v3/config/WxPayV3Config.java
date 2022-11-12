package cn.qmso.wxPay.v3.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 微信支付v3配置
 * @date Date : 2022年11月02日 17:24
 */
@Component
@ConfigurationProperties(prefix = "payment.wxpay.v3")
@Data
public class WxPayV3Config {

    /**
     * v3密钥
     */
    private String key;

    /**
     * 服务商公众号id
     */
    private String spAppId;

    /**
     * 服务商商户号
     */
    private String spMchId;


    /**
     *子商户公众号appid
     */
    private String subAppId;

    /**
     * 子商户商户号
     */
    private String subMchId;

    /**
     * 微信支付公众号appid
     */
    private String appId;

    /**
     * 微信支付商户号
     */
    private String mchId;

    /**
     * 回调地址
     */
    private String notifyUrl;

    /**
     * 证书序列号
     */
    private String serialNo;

    /**
     * 私钥位置
     */
    private String privateKeyPath;
}
