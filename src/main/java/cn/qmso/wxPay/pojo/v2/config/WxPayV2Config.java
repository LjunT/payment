package cn.qmso.wxPay.pojo.v2.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 微信支付v2配置
 * @date Date : 2022年11月02日 15:56
 */
@Configuration
public class WxPayV2Config {

    /**
     *
     */
    private String key;


    public WxPayV2Config(String key) {
        this.key = key;
    }
}
