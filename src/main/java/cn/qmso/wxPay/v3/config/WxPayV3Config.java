package cn.qmso.wxPay.v3.config;

import cn.qmso.wxPay.base.WxPayContent;
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
    private String sp_appid;

    /**
     * 服务商商户号
     */
    private String sp_mchid;


    /**
     *子商户公众号appid
     */
    private String sub_appid;

    /**
     * 子商户商户号
     */
    private String sub_mchid;

    /**
     * 微信支付公众号appid
     */
    private String appid;

    /**
     * 微信支付商户号
     */
    private String mch_id;

    /**
     * 回调地址
     */
    private String notify_url;

    /**
     * 证书序列号
     */
    private String serial_no;

    /**
     * 私钥位置
     */
    private String private_key_path;
}
