package cn.qmso.wxPay.v3;

import cn.qmso.wxPay.base.Pay;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.qmso.wxPay.v2.pojo.only.close.CloseOrderBo;
import cn.qmso.wxPay.v3.pojo.combined.bo.placeorder.CombinedPayBo;
import cn.qmso.wxPay.v3.pojo.combined.vo.notify.CombinedNotfyVo;
import cn.qmso.wxPay.v3.pojo.combined.vo.selectorder.SelectComBinedOrderVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author lijuntao
 * 合单支付
 */
public class WxCombinedPayV3Util extends Pay {
    private static final Logger logger = LoggerFactory.getLogger(WxCombinedPayV3Util.class);

    /**
     * 合单支付下单
     *
     * @param url                请求地址（只需传入域名之后的路由地址）
     * @param combinedPayBo      请求体 json字符串 此参数与微信官方文档一致
     * @param mchId             商户ID
     * @param serialNo          证书序列号
     * @param privateKeyFilePath 私钥的路径
     * @return
     * @throws Exception
     */
    public static String v3CombinedPayGet(String url, CombinedPayBo combinedPayBo, String mchId, String serialNo, String privateKeyFilePath) throws Exception {
        Object body = postRequest(WxPayV3Content.URL_PRE, url, mchId, serialNo,null, privateKeyFilePath, JSONObject.toJSONString(combinedPayBo));
        switch (url) {
            case "v3/combine-transactions/app":
            case "v3/combine-transactions/jsapi":
                return JSONObject.parseObject(body.toString()).getString("prepay_id");
            case "v3/combine-transactions/native":
                return JSONObject.parseObject(body.toString()).getString("code_url");
            case "v3/combine-transactions/h5":
                return JSONObject.parseObject(body.toString()).getString("h5_url");
            default:
                return null;
        }
    }

    /**
     * 处理微信合单支付异步回调
     *
     * @param request
     * @param response
     * @param privateKey 32的秘钥
     * @return 支付的订单号
     */
    public static CombinedNotfyVo notify(HttpServletRequest request, HttpServletResponse response, String privateKey) throws Exception {
        String result = readData(request);
        // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
        String plainText = verifyNotify(result, privateKey);
        //发送消息通知微信
        sendMessage(response, plainText);
        CombinedNotfyVo combinedNotfyVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            combinedNotfyVo = mapper.readValue(plainText,
                    new TypeReference<CombinedNotfyVo>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return combinedNotfyVo;
    }

    /**
     * 查询合单支付订单信息
     *
     * @param mchid 商户ID
     * @return 订单支付成功之后的详细信息
     * @throws Exception
     */
    public static SelectComBinedOrderVo selectOrder(String url, String mchid, String serial_no, String privateKeyFilePath) throws Exception {
        Object body = getRequest(WxPayV3Content.URL_PRE, url,null, mchid, serial_no, privateKeyFilePath);
        SelectComBinedOrderVo selectComBinedOrderVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            selectComBinedOrderVo = mapper.readValue(body.toString(),
                    new TypeReference<SelectComBinedOrderVo>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return selectComBinedOrderVo;
    }

    /**
     * 关闭未支付的合单支付的订单
     *
     * @param url                请求域名
     * @param mchid              商户号
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 证书秘钥地址
     * @return 订单关闭成功，无任何返回
     * @throws Exception
     */
    public static String closeCombinedOrder(String url, String mchid, String serial_no, String privateKeyFilePath, CloseOrderBo closeOrderBo) throws Exception {
        Object body = postRequest(WxPayV3Content.URL_PRE, url, mchid, serial_no,null, privateKeyFilePath, JSONObject.toJSONString(closeOrderBo));
        return body.toString();
    }

}