package com.qm.wxPay.v2;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.qm.wxPay.WxPayContent;
import com.qm.wxPay.WxPayUtil;
import com.qm.wxPay.base.PayV2;
import com.qm.wxPay.pojo.v2.only.close.CloseOrderBo;
import com.qm.wxPay.pojo.v2.only.placeorder.PlaceOrderBo;
import com.qm.wxPay.pojo.v2.only.placeorder.SceneInfo;
import com.qm.wxPay.pojo.v2.only.refund.RefundOrderBo;
import com.qm.wxPay.pojo.v2.only.refund.select.SelectRefundOrderBo;
import com.qm.wxPay.pojo.v2.only.select.SelectOrderBo;
import com.qm.wxPay.pojo.v3.only.vo.initiatepayment.WxPayResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @author lijuntao
 */
public class WxPayV2Util extends PayV2 {

    /**
     * 除付款码支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，
     * 返回正确的预支付交易会话标识后再按Native、JSAPI、APP等不同场景生成交易串调起支付。
     *
     * @param placeOrderBo 请求参数实体
     * @param sceneInfo    请求参数实体
     * @param key          ApiV2秘钥
     * @return
     * @throws Exception
     */
    public static String placeOrder(PlaceOrderBo placeOrderBo, SceneInfo sceneInfo, String key) throws Exception {
        String jsonStr = JSONUtil.toJsonStr(placeOrderBo);
        Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
        map.put("total_fee", placeOrderBo.getTotal_fee() + "");
        if (sceneInfo != null) {
            map.put("scene_info", JSONUtil.toJsonStr(sceneInfo));
        }
        String signedXml = generateSignedXml(map, key, WxPayContent.SIGN_TYPE_MD5);
        Map<String, String> stringStringMap = notCarryCertificateRequestPost(placeOrderBo.getMch_id(), WxPayV2Content.V2PAY_URL, signedXml);
        if (WxPayV2Content.SUCCESS.equals(stringStringMap.get(WxPayV2Content.RETURN_CODE))) {
            switch (placeOrderBo.getTrade_type()) {
                case "JSAPI":
                case "APP":
                    return stringStringMap.get("prepay_id");
                case "NATIVE":
                    return stringStringMap.get("code_url");
                case "MWEB":
                    return stringStringMap.get("h5_url");
                default:
                    return null;
            }
        } else {
            return stringStringMap.toString();
        }
    }

    /**
     * 微信调起支付参数
     *
     * @param prepayId 微信下单返回的prepay_id
     * @param appId    应用ID(appid)
     * @param key      ApiV2秘钥
     * @return 返回参数直接返回
     * @throws Exception
     */
    public static WxPayResult wxTuneUp(String prepayId, String appId, String key) throws Exception {
        String time = (System.currentTimeMillis() / 1000) + "";
        String nonceStr = WxPayUtil.generateNonceStr();
        Map<String, String> map = new HashMap<>();
        map.put("appId", appId);
        map.put("nonceStr", nonceStr );
        map.put("package", "prepay_id=" + prepayId);
        map.put("signType", WxPayContent.SIGN_TYPE_MD5);
        map.put("timeStamp", time);
        String sign = generateSignature(map, key, WxPayContent.SIGN_TYPE_MD5);
        WxPayResult wxPayResult = new WxPayResult(appId, time,nonceStr , "prepay_id=" + prepayId, WxPayContent.SIGN_TYPE_MD5, sign);
        return wxPayResult;
    }

    /**
     * 处理微信异步回调
     *
     * @param request
     * @param response
     * @param privateKey 32的秘钥
     * @return 支付的订单号
     */
    public static Map<String, String> notify(HttpServletRequest request, HttpServletResponse response, String privateKey) throws Exception {
        String result = readData(request);
        Map<String, String> stringStringMap = xmlToMap(result);
        //发送消息通知微信
        sendMessage(response, result);
        return stringStringMap;
    }

    /**
     * 查询订单信息
     *
     * @param selectOrderBo 请求参数实体
     * @param key           ApiV2秘钥
     * @return
     * @throws Exception
     */
    public static Map<String, String> selectOrder(SelectOrderBo selectOrderBo, String key) throws Exception {
        String jsonStr = JSONUtil.toJsonStr(selectOrderBo);
        Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        Map<String, String> stringStringMap = notCarryCertificateRequestPost(selectOrderBo.getMch_id(), WxPayV2Content.V2ORDER_QUERY_URL, signedXml);
        return stringStringMap;
    }

    /**
     * 关闭订单
     *
     * @param closeOrderBo 参数实体
     * @param key          ApiV2秘钥
     * @return
     * @throws Exception
     */
    public static Map<String, String> closeOrder(CloseOrderBo closeOrderBo, String key) throws Exception {
        String jsonStr = JSONUtil.toJsonStr(closeOrderBo);
        Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        Map<String, String> stringStringMap = notCarryCertificateRequestPost(closeOrderBo.getMch_id(), WxPayV2Content.V2ORDER_CLOSE_URL, signedXml);
        return stringStringMap;
    }

    /**
     * 订单退款
     *
     * @param refundOrderBo 参数实体
     * @param key           ApiV2秘钥
     * @return
     * @throws Exception
     */
    public static Map<String, String> refundOrder(RefundOrderBo refundOrderBo, String key , String certPath) throws Exception {
        String jsonStr = JSONUtil.toJsonStr(refundOrderBo);
        Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        Map<String, String> stringStringMap = carryCertificateRequestPost(refundOrderBo.getMch_id(), certPath,WxPayV2Content.V2ORDER_REFUND_URL, signedXml);
        return stringStringMap;
    }

    /**
     * 查询退款订单
     *
     * @param selectRefundOrderBo 参数实体
     * @param key                 ApiV2秘钥
     * @return
     * @throws Exception
     */
    public static Map<String, String> selectRefundOrder(SelectRefundOrderBo selectRefundOrderBo, String key) throws Exception {
        String jsonStr = JSONUtil.toJsonStr(selectRefundOrderBo);
        Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        Map<String, String> stringStringMap = notCarryCertificateRequestPost(selectRefundOrderBo.getMch_id(), WxPayV2Content.V2QUERY_REFUND_ORDER_URL, signedXml);
        return stringStringMap;
    }

}