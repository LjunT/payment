package cn.qmso.wxPay.v3;

import cn.qmso.wxPay.base.Pay;
import cn.qmso.wxPay.base.WxPayUtil;
import cn.qmso.wxPay.v3.config.WxPayV3Config;
import cn.qmso.wxPay.v3.pojo.only.bo.bill.FundflowbillBo;
import cn.qmso.wxPay.v3.pojo.only.bo.bill.TradebillBo;
import cn.qmso.wxPay.v3.pojo.only.bo.selectOrder.SelectOrderBo;
import cn.qmso.wxPay.v3.pojo.only.bo.placeorder.WxPayRequestBo;
import cn.qmso.wxPay.v3.pojo.only.bo.refund.RefundBo;
import cn.qmso.wxPay.v3.pojo.only.vo.notify.NotifyVo;
import cn.qmso.wxPay.v3.pojo.only.vo.refund.RefundVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.qmso.wxPay.v3.pojo.only.vo.apply.fundbill.FundBillVo;
import cn.qmso.wxPay.v3.pojo.only.vo.apply.transactionbill.TransactionBillVo;
import cn.qmso.wxPay.v3.pojo.only.vo.initiatepayment.WxPayResult;
import cn.qmso.wxPay.v3.pojo.only.vo.refundnotify.RefundOrderNotifyVo;
import cn.qmso.wxPay.v3.pojo.only.vo.selectorder.SelectOrderVo;
import cn.qmso.wxPay.v3.pojo.only.vo.selectrefund.SelectRefundVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author lijuntao
 */
@Component
public class WxPayV3 extends Pay {

    private static final Logger logger = LoggerFactory.getLogger(WxPayV3.class);

    @Autowired
    private WxPayV3Config defaultWxPayV3Config;

    /**
     * 微信支付下单
     * @param url                请求地址（只需传入域名之后的路由地址）
     * @param wxPayRequestBo     请求体 json字符串 此参数与微信官方文档一致
     * @return 订单支付的参数
     * @throws Exception 异常
     */
    public String v3PayGet(String url, WxPayRequestBo wxPayRequestBo) throws Exception {
        return v3PayGet(url, wxPayRequestBo,defaultWxPayV3Config);
    }

    /**
     * 微信支付下单
     *
     * @param url                请求地址（只需传入域名之后的路由地址）
     * @param wxPayRequestBo     请求体 json字符串 此参数与微信官方文档一致
     * @return 订单支付的参数
     * @throws Exception
     */
    public String v3PayGet(String url, WxPayRequestBo wxPayRequestBo, WxPayV3Config wxPayV3Config) throws Exception {
        if (wxPayV3Config == null){
            wxPayV3Config = defaultWxPayV3Config;
        }
        JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(wxPayRequestBo));
        object.put("appid",wxPayV3Config.getAppid());
        object.put("mchid",wxPayV3Config.getMch_id());
        if (StringUtils.isEmpty(wxPayRequestBo.getNotify_url())){
            object.put("notify_url",wxPayV3Config.getNotify_url());
        }
        Object body = postRequest(WxPayV3Content.URL_PRE,
                url,
                wxPayV3Config.getMch_id(),
                wxPayV3Config.getSerial_no(),
                null,
                wxPayV3Config.getPrivate_key_path(),
                JSONObject.toJSONString(object));
        switch (url) {
            case WxPayV3Content.V3_APP_PAY_URL:
            case WxPayV3Content.V3_JSAPI_PAY_URL:
                return JSONObject.parseObject(body.toString()).getString("prepay_id");
            case WxPayV3Content.V3_NATIVE_PAY_URL:
                return JSONObject.parseObject(body.toString()).getString("code_url");
            case WxPayV3Content.V3_H5_PAY_URL:
                return JSONObject.parseObject(body.toString()).getString("h5_url");
            default:
                return null;
        }
    }

    /**
     * 微信调起支付参数
     * @param prepayId           微信下单返回的prepay_id
     * @return 当前调起支付所需的参数
     * @throws Exception 异常
     */
    public WxPayResult wxTuneUp(String prepayId) throws Exception {
       return wxTuneUp(prepayId,defaultWxPayV3Config);
    }

    /**
     * 微信调起支付参数
     * @param prepayId 微信下单返回的prepay_id
     * @param wxPayV3Config 配置信息
     * @return 当前调起支付所需的参数
     * @throws Exception 异常
     */
    public WxPayResult wxTuneUp(String prepayId, WxPayV3Config wxPayV3Config) throws Exception {
        if (wxPayV3Config == null){
            wxPayV3Config = defaultWxPayV3Config;
        }
        String time = System.currentTimeMillis() / 1000 + "";
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String packageStr = "prepay_id=" + prepayId;
        ArrayList<String> list = new ArrayList<>();
        list.add(wxPayV3Config.getAppid());
        list.add(time);
        list.add(nonceStr);
        list.add(packageStr);
        //加载签名
        String packageSign = sign(buildSignMessage(list).getBytes(), wxPayV3Config.getPrivate_key_path());
        return new WxPayResult(wxPayV3Config.getAppid(),
                time,
                nonceStr,
                "prepay_id=" + prepayId,
                "RSA",
                packageSign);
    }

    /**
     * 处理微信异步回调
     *
     * @param request
     * @param response
     * @return 支付的订单号
     */
    public NotifyVo notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
       return notify(request,response,defaultWxPayV3Config);
    }

    /**
     * 处理微信异步回调
     *
     * @param request
     * @param response
     * @param wxPayV3Config 配置细腻
     * @return 支付的订单号
     */
    public NotifyVo notify(HttpServletRequest request, HttpServletResponse response, WxPayV3Config wxPayV3Config) throws Exception {
        if (wxPayV3Config == null){
            wxPayV3Config = defaultWxPayV3Config;
        }
        String result = readData(request);
        // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
        String plainText = verifyNotify(result, wxPayV3Config.getPrivate_key_path());
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
     * @param out_trade_no 商户订单号
     * @param transactionId 微信订单号
     * @return 订单支付成功之后的详细信息
     * @throws Exception
     */
    public SelectOrderVo selectOrder(String transactionId,String out_trade_no) throws Exception {
        return selectOrder(transactionId, out_trade_no,defaultWxPayV3Config);
    }

    /**
     * 查询订单信息
     * @param wxPayV3Config 配置信息
     * @param out_trade_no 商户订单号
     * @param transactionId 微信订单号
     * @return 订单支付成功之后的详细信息
     * @throws Exception
     */
    public SelectOrderVo selectOrder(String transactionId,String out_trade_no,WxPayV3Config wxPayV3Config) throws Exception {
        if (wxPayV3Config == null){
            wxPayV3Config = defaultWxPayV3Config;
        }
        String url = "";
        if (transactionId != null){
            url = WxPayV3Content.V3_QUERY_TRANSACTIONS + transactionId;
        }
        if (out_trade_no != null){
            url = WxPayV3Content.V3_QUERY_OUT_TRADE_NO + out_trade_no;
        }
        Object body = getRequest(WxPayV3Content.URL_PRE,
                url,
                new SelectOrderBo(wxPayV3Config.getMch_id()),
                wxPayV3Config.getMch_id(),
                wxPayV3Config.getSerial_no(),
                wxPayV3Config.getPrivate_key_path());

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
     * @param out_trade_no 商户订单号
     * @return 订单关闭成功，无任何返回
     * @throws Exception 异常
     */
    public String closeOrder(String out_trade_no) throws Exception {
        return closeOrder(out_trade_no,defaultWxPayV3Config);
    }

    /**
     * 关闭未支付的订单
     * @param out_trade_no 商户订单号
     * @param wxPayV3Config 微信配置
     * @return 订单关闭成功，无任何返回
     * @throws Exception 异常
     */
    public String closeOrder(String out_trade_no, WxPayV3Config wxPayV3Config) throws Exception {
        if (wxPayV3Config == null){
            wxPayV3Config = defaultWxPayV3Config;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mchid", wxPayV3Config.getMch_id());
        String url = String.format(WxPayV3Content.V3_CLOSE_ORDER_URL, out_trade_no);
        Object body = postRequest(WxPayV3Content.URL_PRE,
                url,
                wxPayV3Config.getMch_id(),
                wxPayV3Config.getSerial_no(),
                null,
                wxPayV3Config.getPrivate_key_path(),
                jsonObject.toString());
        return body.toString();
    }

    /**
     * 订单退款
     * @param refundBo 请求参数
     * @return 返回退款详细信息
     * @throws Exception 异常
     */
    public RefundVo refundOrder(RefundBo refundBo) throws Exception {
        return refundOrder(refundBo,defaultWxPayV3Config);
    }

    /**
     * 订单退款
     * @param wxPayV3Config 配置信息
     * @param refundBo           请求参数json
     * @return 返回退款详细信息
     * @throws Exception 异常
     */
    public RefundVo refundOrder(RefundBo refundBo , WxPayV3Config wxPayV3Config) throws Exception {
        if (wxPayV3Config == null){
            wxPayV3Config = defaultWxPayV3Config;
        }
        Object body = postRequest(WxPayV3Content.URL_PRE,
                WxPayV3Content.V3_REFUND_URL,
                wxPayV3Config.getMch_id(),
                wxPayV3Config.getSerial_no(),
                null,
                wxPayV3Config.getPrivate_key_path(),
                JSONObject.toJSONString(refundBo));
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
     * @return
     * @throws Exception
     */
    public RefundOrderNotifyVo refundOrderNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return refundOrderNotify(request, response,defaultWxPayV3Config);
    }

    /**
     * 退款的异步回调处理
     *
     * @param request
     * @param response
     * @param wxPayV3Config 配置信息
     * @return
     * @throws Exception
     */
    public RefundOrderNotifyVo refundOrderNotify(HttpServletRequest request, HttpServletResponse response, WxPayV3Config wxPayV3Config) throws Exception {
        if (wxPayV3Config == null){
            wxPayV3Config = defaultWxPayV3Config;
        }
        String result = readData(request);
        String plainText = verifyNotify(result, wxPayV3Config.getKey());
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
     * @param out_refund_no 退款订单号
     * @return 查询订单退款信息的详细
     * @throws Exception
     */
    public SelectRefundVo selectRefundOrder(String out_refund_no) throws Exception {
        return selectRefundOrder(out_refund_no,defaultWxPayV3Config);
    }

    /**
     * 查询退款订单信息
     * @param out_refund_no 退款订单号
     * @param wxPayV3Config 配置信息
     * @return 查询订单退款信息的详细
     * @throws Exception
     */
    public SelectRefundVo selectRefundOrder(String out_refund_no,WxPayV3Config wxPayV3Config) throws Exception {
        if (wxPayV3Config == null){
            wxPayV3Config = defaultWxPayV3Config;
        }
        String url = String.format(WxPayV3Content.V3_SELECT_REFUND_URL, out_refund_no);
        Object body = getRequest(WxPayV3Content.URL_PRE,
                url,
                null,
                wxPayV3Config.getMch_id(),
                wxPayV3Config.getSerial_no(),
                wxPayV3Config.getPrivate_key_path());
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
     * @param tradebillBo 查询交易账单参数
     * @return 返回参数信息
     * @throws Exception
     */
    public TransactionBillVo applyTransactionBill(TradebillBo tradebillBo) throws Exception {
        return applyTransactionBill(tradebillBo,defaultWxPayV3Config);
    }

    /**
     * 申请交易账单
     * @param tradebillBo 查询交易账单参数
     * @param wxPayV3Config 配置信息
     * @return 返回参数信息
     * @throws Exception
     */
    public TransactionBillVo applyTransactionBill(TradebillBo tradebillBo,WxPayV3Config wxPayV3Config) throws Exception {
        if (wxPayV3Config == null){
            wxPayV3Config = defaultWxPayV3Config;
        }
        Object body = getRequest(WxPayV3Content.URL_PRE,
                WxPayV3Content.V3_TRADEBILL_URL,
                tradebillBo,
                wxPayV3Config.getMch_id(),
                wxPayV3Config.getSerial_no(),
                wxPayV3Config.getPrivate_key_path());
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
     * @param fundflowbillBo 申请资金账单参数
     * @return 返回参数信息
     * @throws Exception
     */
    public FundBillVo applyFundBill(FundflowbillBo fundflowbillBo) throws Exception {
       return applyFundBill(fundflowbillBo,defaultWxPayV3Config);
    }

    /**
     * 申请资金账单
     * @param fundflowbillBo 申请资金账单参数
     * @param wxPayV3Config 配置信息
     * @return 返回参数信息
     * @throws Exception
     */
    public FundBillVo applyFundBill(FundflowbillBo fundflowbillBo,WxPayV3Config wxPayV3Config) throws Exception {
        if (wxPayV3Config == null){
            wxPayV3Config = defaultWxPayV3Config;
        }
        Object body = getRequest(WxPayV3Content.URL_PRE,
                WxPayV3Content.V3_FUNDFLOWBILL_URL,
                fundflowbillBo,
                wxPayV3Config.getMch_id(),
                wxPayV3Config.getSerial_no(),
                wxPayV3Config.getPrivate_key_path());
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