package com.qm.wxPay.v3;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.qm.wxPay.base.Pay;
import com.qm.wxPay.pojo.v3.only.bo.placeorder.WxPayRequestBo;
import com.qm.wxPay.pojo.v3.only.bo.refund.RefundBo;
import com.qm.wxPay.pojo.v3.only.vo.apply.fundbill.FundBillVo;
import com.qm.wxPay.pojo.v3.only.vo.apply.transactionbill.TransactionBillVo;
import com.qm.wxPay.pojo.v3.only.vo.initiatepayment.WxPayResult;
import com.qm.wxPay.pojo.v3.only.vo.notify.NotifyVo;
import com.qm.wxPay.pojo.v3.only.vo.refund.RefundVo;
import com.qm.wxPay.pojo.v3.only.vo.refundnotify.RefundOrderNotifyVo;
import com.qm.wxPay.pojo.v3.only.vo.selectorder.SelectOrderVo;
import com.qm.wxPay.pojo.v3.only.vo.selectrefund.SelectRefundVo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.UUID;

/**
 * 〈一句话功能简述〉<br>
 * 〈微信支付V3请求〉
 *
 * @author Gym
 * @create 2020/12/18
 * @since 1.0.0
 */
public class WxPayV3Util extends Pay {

    private static final Logger logger = LoggerFactory.getLogger(WxPayV3Util.class);

    /**
     * 微信支付下单
     *
     * @param url                请求地址（只需传入域名之后的路由地址）
     * @param wxPayRequestBo     请求体 json字符串 此参数与微信官方文档一致
     * @param mercId             商户ID
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 私钥的路径
     * @return 订单支付的参数
     * @throws Exception
     */
    public static String v3PayGet(String url, WxPayRequestBo wxPayRequestBo, String mercId, String serial_no, String privateKeyFilePath) throws Exception {
        Object body = postRequest(WxPayV3Content.URL_PRE,url, mercId, serial_no,null, privateKeyFilePath, JSONUtil.toJsonStr(wxPayRequestBo));
        switch (url) {
            case WxPayV3Content.V3_APP_PAY_URL:
            case WxPayV3Content.V3_JSAPI_PAY_URL:
                return JSONObject.fromObject(body).getString("prepay_id");
            case WxPayV3Content.V3_NATIVE_PAY_URL:
                return JSONObject.fromObject(body).getString("code_url");
            case WxPayV3Content.V3_H5_PAY_URL:
                return JSONObject.fromObject(body).getString("h5_url");
            default:
                return null;
        }
    }


    /**
     * 微信调起支付参数
     *
     * @param prepayId           微信下单返回的prepay_id
     * @param appId              应用ID(appid)
     * @param privateKeyFilePath 私钥的地址
     * @return 当前调起支付所需的参数
     * @throws Exception
     */
    public static WxPayResult wxTuneUp(String prepayId, String appId, String privateKeyFilePath) throws Exception {
        String time = System.currentTimeMillis() / 1000 + "";
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String packageStr = "prepay_id=" + prepayId;
        ArrayList<String> list = new ArrayList<>();
        list.add(appId);
        list.add(time);
        list.add(nonceStr);
        list.add(packageStr);
        //加载签名
        String packageSign = sign(buildSignMessage(list).getBytes(), privateKeyFilePath);
        WxPayResult wxPayResult = new WxPayResult(appId, time, nonceStr, packageStr, "RSA", packageSign);
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
    public static NotifyVo notify(HttpServletRequest request, HttpServletResponse response, String privateKey) throws Exception {
        String result = readData(request);
        // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
        String plainText = verifyNotify(result, privateKey);
        //发送消息通知微信
        sendMessage(response, plainText);
        NotifyVo notifyVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            notifyVo = mapper.readValue(plainText,
                    new TypeReference<NotifyVo>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return notifyVo;
    }


    /**
     * 查询订单信息
     *
     * @param url                请求域名
     * @param mchid              商户ID
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 证书秘钥地址
     * @return 订单支付成功之后的详细信息
     * @throws Exception
     */
    public static SelectOrderVo selectOrder(String url, String mchid, String serial_no, String privateKeyFilePath) throws Exception {
        Object body = getRequest(WxPayV3Content.URL_PRE, url,null, mchid, serial_no, privateKeyFilePath);
        SelectOrderVo selectOrderVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            selectOrderVo = mapper.readValue(body.toString(),
                    new TypeReference<SelectOrderVo>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return selectOrderVo;
    }

    /**
     * 关闭未支付的订单
     *
     * @param url                请求域名
     * @param mchid              商户号
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 证书秘钥地址
     * @return 订单关闭成功，无任何返回
     * @throws Exception
     */
    public static String closeOrder(String url, String mchid, String serial_no, String privateKeyFilePath) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mchid", mchid);
        Object body = postRequest(WxPayV3Content.URL_PRE, url, mchid, serial_no,null, privateKeyFilePath, jsonObject.toString());
        return body.toString();
    }

    /**
     * 订单退款
     *
     * @param url                请求域名
     * @param mchid              商户号
     * @param serial_no          证书序列号
     * @param refundBo           请求参数json
     * @param privateKeyFilePath 证书秘钥地址
     * @return 返回退款详细信息
     * @throws Exception
     */
    public static RefundVo refundOrder(String url, String mchid, String serial_no, RefundBo refundBo, String privateKeyFilePath) throws Exception {
        Object body = postRequest(WxPayV3Content.URL_PRE, url, mchid, serial_no,null, privateKeyFilePath, JSONUtil.toJsonStr(refundBo));
        RefundVo refundVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            refundVo = mapper.readValue(body.toString(),
                    new TypeReference<RefundVo>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return refundVo;
    }

    /**
     * 退款的异步回调处理
     *
     * @param request
     * @param response
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static RefundOrderNotifyVo refundOrderNotify(HttpServletRequest request, HttpServletResponse response, String privateKey) throws Exception {
        String result = readData(request);
        String plainText = verifyNotify(result, privateKey);
        //发送消息通知微信
        sendMessage(response, plainText);
        RefundOrderNotifyVo refundOrderNotifyVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            refundOrderNotifyVo = mapper.readValue(plainText,
                    new TypeReference<RefundOrderNotifyVo>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return refundOrderNotifyVo;
    }

    /**
     * 查询退款订单信息
     *
     * @param url                请求域名
     * @param mchid              商户号
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 证书秘钥地址
     * @return 查询订单退款信息的详细
     * @throws Exception
     */
    public static SelectRefundVo selectRefundOrder(String url, String mchid, String serial_no, String privateKeyFilePath) throws Exception {
        Object body = getRequest(WxPayV3Content.URL_PRE, url,null, mchid, serial_no, privateKeyFilePath);
        SelectRefundVo selectRefundVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            selectRefundVo = mapper.readValue(body.toString(),
                    new TypeReference<SelectRefundVo>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return selectRefundVo;
    }

    /**
     * 申请交易账单
     *
     * @param url                请求域名
     * @param mchid              商户号
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 证书秘钥地址
     * @return 返回参数信息
     * @throws Exception
     */
    public static TransactionBillVo applyTransactionBill(String url, String mchid, String serial_no, String privateKeyFilePath) throws Exception {
        Object body = getRequest(WxPayV3Content.URL_PRE, url,null, mchid, serial_no, privateKeyFilePath);
        TransactionBillVo transactionBillVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            transactionBillVo = mapper.readValue(body.toString(),
                    new TypeReference<TransactionBillVo>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return transactionBillVo;
    }

    /**
     * 申请资金账单
     *
     * @param url                请求域名
     * @param mchid              商户号
     * @param serial_no          证书序列号
     * @param privateKeyFilePath 证书秘钥地址
     * @return 返回参数信息
     * @throws Exception
     */
    public static FundBillVo applyFundBill(String url, String mchid, String serial_no, String privateKeyFilePath) throws Exception {
        Object body = getRequest(WxPayV3Content.URL_PRE, url,null, mchid, serial_no, privateKeyFilePath);
        FundBillVo fundBillVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            fundBillVo = mapper.readValue(body.toString(),
                    new TypeReference<FundBillVo>() {
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return fundBillVo;
    }
}