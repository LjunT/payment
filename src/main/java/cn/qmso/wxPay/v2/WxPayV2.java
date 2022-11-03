package cn.qmso.wxPay.v2;

import cn.hutool.json.JSONUtil;
import cn.qmso.wxPay.base.PayV2;
import cn.qmso.wxPay.v2.config.WxPayV2Config;
import cn.qmso.wxPay.v2.pojo.WxPayV2Content;
import cn.qmso.wxPay.v2.pojo.only.placeorder.SceneInfo;
import cn.qmso.wxPay.v2.pojo.only.refund.select.SelectRefundOrderBo;
import cn.qmso.wxPay.base.WxPayUtil;
import cn.qmso.wxPay.v2.pojo.only.close.CloseOrderBo;
import cn.qmso.wxPay.v2.pojo.only.placeorder.PlaceOrderBo;
import cn.qmso.wxPay.v2.pojo.only.refund.RefundOrderBo;
import cn.qmso.wxPay.v2.pojo.only.select.SelectOrderBo;
import cn.qmso.wxPay.v3.pojo.only.vo.initiatepayment.WxPayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @author lijuntao
 */
@Component
public class WxPayV2 extends PayV2 {


    @Autowired
    private WxPayV2Config defaultWxPayV2Config;


    /**
     * 除付款码支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，
     * 返回正确的预支付交易会话标识后再按Native、JSAPI、APP等不同场景生成交易串调起支付。
     * @param placeOrderBo 请求参数实体
     * @param sceneInfo    请求参数实体
     * @throws Exception
     */
    public String placeOrder(PlaceOrderBo placeOrderBo, SceneInfo sceneInfo) throws Exception {
        return placeOrder(placeOrderBo, sceneInfo,defaultWxPayV2Config);
    }

    /**
     * 除付款码支付场景以外，商户系统先调用该接口在微信支付服务后台生成预支付交易单，
     * 返回正确的预支付交易会话标识后再按Native、JSAPI、APP等不同场景生成交易串调起支付。
     * @param placeOrderBo 请求参数实体
     * @param sceneInfo    请求参数实体
     * @param wxPayV2Config v2配置
     * @throws Exception 异常
     */
    public String placeOrder(PlaceOrderBo placeOrderBo, SceneInfo sceneInfo, WxPayV2Config wxPayV2Config) throws Exception {
        if (wxPayV2Config == null){
            wxPayV2Config = defaultWxPayV2Config;
        }
        Map<String, String> map = WxPayUtil.objectToMap(placeOrderBo);
        map.put("total_fee", placeOrderBo.getTotal_fee() + "");
        map.put("appid", wxPayV2Config.getAppid());
        map.put("mch_id", wxPayV2Config.getMch_id());
        map.put("nonce_str",WxPayUtil.generateNonceStr());
        map.put("sign_type",wxPayV2Config.getSign_type());
        if (sceneInfo != null) {
            map.put("scene_info", JSONUtil.toJsonStr(sceneInfo));
        }
        String signedXml = generateSignedXml(map, wxPayV2Config.getKey(), wxPayV2Config.getSign_type());
        Map<String, String> stringStringMap = notCarryCertificateRequestPost(wxPayV2Config.getMch_id(),
                                                                             WxPayV2Content.V2PAY_URL,
                                                                             signedXml);
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
     * @return 返回参数直接返回
     * @throws Exception
     */
    public WxPayResult wxTuneUp(String prepayId) throws Exception {
        return wxTuneUp(prepayId,defaultWxPayV2Config);
    }

    /**
     * 微信调起支付参数
     *
     * @param prepayId 微信下单返回的prepay_id
     * @return 返回参数直接返回
     * @throws Exception
     */
    public WxPayResult wxTuneUp(String prepayId, WxPayV2Config wxPayV2Config) throws Exception {
        if (wxPayV2Config == null){
            wxPayV2Config = defaultWxPayV2Config;
        }
        String time = (System.currentTimeMillis() / 1000) + "";
        String nonceStr = WxPayUtil.generateNonceStr();
        Map<String, String> map = new HashMap<>();
        map.put("appId", wxPayV2Config.getAppid());
        map.put("nonceStr", nonceStr );
        map.put("package", "prepay_id=" + prepayId);
        map.put("signType", wxPayV2Config.getSign_type());
        map.put("timeStamp", time);
        String sign = generateSignature(map, wxPayV2Config.getKey(), wxPayV2Config.getSign_type());
        return new WxPayResult(wxPayV2Config.getAppid(),
                time,nonceStr ,
                "prepay_id=" + prepayId,
                wxPayV2Config.getSign_type(),
                sign);
    }

    /**
     * 处理微信异步回调
     *
     * @param request 请求信息
     * @param response 响应信息
     * @return 支付的订单号
     */
    public static Map<String, String> notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = readData(request);
        Map<String, String> stringStringMap = xmlToMap(result);
        //发送消息通知微信
        sendMessage(response, result);
        return stringStringMap;
    }


    /**
     * 查询订单信息
     * @param selectOrderBo 请求参数实体
     * @return 订单信息
     * @throws Exception 异常信息
     */
    public Map<String, String> selectOrder(SelectOrderBo selectOrderBo) throws Exception {
        return selectOrder(selectOrderBo,defaultWxPayV2Config);
    }

    /**
     * 查询订单信息
     *
     * @param selectOrderBo 请求参数实体
     * @param wxPayV2Config 配置信息
     * @return 订单信息
     * @throws Exception 异常信息
     */
    public Map<String, String> selectOrder(SelectOrderBo selectOrderBo, WxPayV2Config wxPayV2Config) throws Exception {
        if (wxPayV2Config == null){
            wxPayV2Config = defaultWxPayV2Config;
        }
        Map<String, String> map = WxPayUtil.objectToMap(selectOrderBo);
        String signedXml = generateSignedXml(map, wxPayV2Config.getKey(),wxPayV2Config.getSign_type());
        return notCarryCertificateRequestPost(selectOrderBo.getMch_id(), WxPayV2Content.V2ORDER_QUERY_URL, signedXml);
    }


    /**
     * 关闭订单
     * @param closeOrderBo 参数实体
     * @return 关闭结果
     * @throws Exception 异常信息
     */
    public Map<String, String> closeOrder(CloseOrderBo closeOrderBo) throws Exception {
       return closeOrder(closeOrderBo,defaultWxPayV2Config);
    }


    /**
     * 关闭订单
     *
     * @param closeOrderBo 参数实体
     * @param wxPayV2Config 配置信息
     * @return 微信返回信息
     * @throws Exception 异常
     */
    public Map<String, String> closeOrder(CloseOrderBo closeOrderBo,WxPayV2Config wxPayV2Config) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(closeOrderBo);
        String signedXml = generateSignedXml(map, wxPayV2Config.getKey(),wxPayV2Config.getSign_type());
        return notCarryCertificateRequestPost(closeOrderBo.getMch_id(), WxPayV2Content.V2ORDER_CLOSE_URL, signedXml);
    }


    /**
     * 订单退款
     *
     * @param refundOrderBo 参数实体
     * @return 退款信息
     * @throws Exception 发生错误
     */
    public Map<String, String> refundOrder(RefundOrderBo refundOrderBo) throws Exception {
       return refundOrder(refundOrderBo,defaultWxPayV2Config);
    }

    /**
     * 订单退款
     *
     * @param refundOrderBo 参数实体
     * @param wxPayV2Config 配置信息
     * @return 退款信息
     * @throws Exception 异常
     */
    public Map<String, String> refundOrder(RefundOrderBo refundOrderBo, WxPayV2Config wxPayV2Config) throws Exception {
        if (wxPayV2Config == null){
            wxPayV2Config = defaultWxPayV2Config;
        }
        Map<String, String> map = WxPayUtil.objectToMap(refundOrderBo);
        map.put("appid",wxPayV2Config.getAppid());
        map.put("mch_id",wxPayV2Config.getMch_id());
        map.put("nonce_str",WxPayUtil.generateNonceStr());
        map.put("sign_type",wxPayV2Config.getSign_type());
        String signedXml = generateSignedXml(map, wxPayV2Config.getKey(),wxPayV2Config.getSign_type());
        return carryCertificateRequestPost(wxPayV2Config.getMch_id(), wxPayV2Config.getCert_path(),WxPayV2Content.V2ORDER_REFUND_URL, signedXml);
    }

    /**
     * 查询退款订单
     * @param selectRefundOrderBo 参数实体
     * @return 返回信息
     * @throws Exception 异常
     */
    public Map<String, String> selectRefundOrder(SelectRefundOrderBo selectRefundOrderBo) throws Exception {
       return selectRefundOrder(selectRefundOrderBo,defaultWxPayV2Config);
    }

    /**
     * 查询退款订单
     *
     * @param selectRefundOrderBo 参数实体
     * @param wxPayV2Config 配置信息
     * @return 返回信息
     * @throws Exception 异常
     */
    public Map<String, String> selectRefundOrder(SelectRefundOrderBo selectRefundOrderBo, WxPayV2Config wxPayV2Config) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(selectRefundOrderBo);
        String signedXml = generateSignedXml(map, wxPayV2Config.getKey(),wxPayV2Config.getSign_type());
        return notCarryCertificateRequestPost(selectRefundOrderBo.getMch_id(), WxPayV2Content.V2QUERY_REFUND_ORDER_URL, signedXml);
    }

}