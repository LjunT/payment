package cn.qmso;

import cn.qmso.wxPay.v2.WxPayV2;
import cn.qmso.wxPay.v2.config.WxPayV2Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 自动装配
 * @date Date : 2022年11月03日 9:50
 */
@Configuration
public class PaymentAutoConfig {

    @Bean
    @ConditionalOnMissingBean(WxPayV2.class)
    public WxPayV2 wxPayV2(){
        return new WxPayV2();
    }

    @Bean
    @ConditionalOnMissingBean(WxPayV2Config.class)
    public WxPayV2Config wxPayV2Config(){
        return new WxPayV2Config();
    }
}