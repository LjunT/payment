package cn.qmso.wxPay.v2;

import cn.qmso.wxPay.base.PayV2;
import cn.qmso.wxPay.base.WxPayUtil;
import cn.qmso.wxPay.v2.config.WxPayV2Config;
import cn.qmso.wxPay.v2.pojo.WxPayV2Content;
import cn.qmso.wxPay.v2.pojo.transfer.promotion.TransferPersonalBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 商家转账
 * @date Date : 2022年11月10日 17:39
 */
@Component
public class WxTransferV2 extends PayV2 {

    @Autowired
    private WxPayV2Config defaultWxPayV2Config;



    /**
     * 查询企业付款到零钱订单
     * @param partnerTradeNo 商户订单号
     * @return 查询结果
     * @throws Exception 异常
     */
    public Map<String,String> selectTransfer(String partnerTradeNo) throws Exception {
        return selectTransfer(partnerTradeNo,defaultWxPayV2Config);
    }

    /**
     * 查询企业付款到零钱订单
     * @param partnerTradeNo 商户订单号
     * @param wxPayV2Config 微信配置
     * @return 查询结果
     * @throws Exception 异常
     */
    public Map<String,String> selectTransfer(String partnerTradeNo,WxPayV2Config wxPayV2Config) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("nonce_str",WxPayUtil.generateNonceStr());
        map.put("partner_trade_no",partnerTradeNo);
        map.put("mch_id",wxPayV2Config.getMch_id());
        map.put("appid",wxPayV2Config.getAppid());
        String signedXml = generateSignedXml(map, wxPayV2Config.getKey(), wxPayV2Config.getSign_type());
        return carryCertificateRequestPost(wxPayV2Config.getMch_id(),wxPayV2Config.getCert_path(),WxPayV2Content.V2QUERY_TRANSFER_URL,signedXml);
    }


    /**
     * 企业付款到个人
     * @param transferPersonalBo 付款参数
     * @return 付款结果
     * @throws Exception 异常
     */
    public Map<String, String> transfer(TransferPersonalBo transferPersonalBo) throws Exception {
        return transfer(transferPersonalBo,defaultWxPayV2Config);
    }

    /**
     * 企业付款到个人
     * @param transferPersonalBo 付款参数
     * @param wxPayV2Config 微信配置
     * @return 付款结果
     * @throws Exception 异常
     */
    public Map<String, String> transfer(TransferPersonalBo transferPersonalBo,WxPayV2Config wxPayV2Config) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(transferPersonalBo);
        map.put("mch_appid",wxPayV2Config.getAppid());
        map.put("mchid",wxPayV2Config.getMch_id());
        map.put("nonce_str",WxPayUtil.generateNonceStr());
        String signedXml = generateSignedXml(map, wxPayV2Config.getKey(), wxPayV2Config.getSign_type());
        return carryCertificateRequestPost(wxPayV2Config.getMch_id(), wxPayV2Config.getCert_path(), WxPayV2Content.V2TRANSFER_URL, signedXml);
    }

}
