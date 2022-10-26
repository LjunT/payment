package com.qm.wxPay;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 微信通用常量
 * @date Date : 2022年05月12日 10:00
 */
@Slf4j
public class WxPayContent {

    /**
     * 微信验签加密方式 MD5
     */
    public static final String SIGN_TYPE_MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";

    /**
     * 微信验签加密方式 HMACSHA256
     */
    public static final String SIGN_TYPE_HMAC_SHA256 = "HMACSHA256";

    /**
     * 微信支付货币 CNY
     */
    public static final String CNY = "CNY";
}
