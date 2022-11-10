package cn.qmso.wxPay.base;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 微信支付 工具类
 * @date Date : 2022年05月12日 9:56
 */
@Slf4j
public class WxPayUtil {

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public static String generateNonceStr() {
        String symbols = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new SecureRandom();

        char[] nonceChars = new char[32];
        for (int index = 0; index < nonceChars.length; ++index) {
            nonceChars[index] = symbols.charAt(random.nextInt(symbols.length()));
        }
        return new String(nonceChars);
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data 待签名数据
     * @param key API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, Object> data, String key, String signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if ("sign".equals(k)) {
                continue;
            }
            // 参数值为空，则不参与签名
            if (data.get(k) != null)
            {
                sb.append(k).append("=").append(data.get(k)).append("&");
            }
        }
        sb.append("key=").append(key);
        if (WxPayContent.SIGN_TYPE_MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        }
        else if (WxPayContent.SIGN_TYPE_HMAC_SHA256.equals(signType)) {
            return HMACSHA256(sb.toString(), key);
        }
        else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }


    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }


    /**
     * 对象转map
     * @param object 独享
     * @return map
     */
    public static Map<String, String> objectToMap(Object object){
        Map<String,String> dataMap = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(object) != null){
                    dataMap.put(field.getName(),field.get(object).toString());
                }
            } catch (IllegalAccessException e) {
                log.error("对象转换失败");
            }
        }
        return dataMap;
    }


    /**
     * 对象转map
     * @param object 独享
     * @return map
     */
    public static Map<String, Object> objectToMapObj(Object object){
        Map<String,Object> dataMap = new HashMap<>();
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if (field.get(object) != null){
                    dataMap.put(field.getName(),field.get(object));
                }
            } catch (IllegalAccessException e) {
                log.error("对象转换失败");
            }
        }
        return dataMap;
    }

}
