package cn.qmso.wxPay.v2;

import cn.qmso.wxPay.WxPayException;
import cn.qmso.wxPay.base.PayV2;
import cn.qmso.wxPay.base.WxPayUtil;
import cn.qmso.wxPay.v2.config.WxPayV2Config;
import cn.qmso.wxPay.v2.pojo.WxPayV2Content;
import cn.qmso.wxPay.v2.pojo.transfer.bank.TransferBank;
import cn.qmso.wxPay.v2.pojo.transfer.promotion.TransferPersonalBo;
import org.apache.commons.lang3.StringUtils;
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
     * 查询付款到银行卡
     * @param partnerTradeNo 商户订单号
     * @return 请求结果
     * @throws Exception 异常
     */
    public Map<String,String> queryTransferBank(String partnerTradeNo) throws Exception {
        return queryTransferBank(partnerTradeNo,defaultWxPayV2Config);
    }

    /**
     * 查询付款到银行卡
     * @param partnerTradeNo 商户订单号
     * @param wxPayV2Config 配置信息
     * @return 请求结果
     * @throws Exception 异常
     */
    public Map<String,String> queryTransferBank(String partnerTradeNo,WxPayV2Config wxPayV2Config) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("mch_id",wxPayV2Config.getMchId());
        map.put("partner_trade_no",partnerTradeNo);
        map.put("nonce_str",WxPayUtil.generateNonceStr());
        String signedXml = generateSignedXml(map, wxPayV2Config);
        return carryCertificateRequestPost(wxPayV2Config,WxPayV2Content.QUERY_TRANSFER_BANK_URL,signedXml);
    }


    /**
     * 付款到银行卡
     * @param transferBank 付款到银行卡参数
     * @return 请求结果
     * @throws Exception 异常
     */
    public Map<String,String> transferBank(TransferBank transferBank) throws Exception {
        return transferBank(transferBank,defaultWxPayV2Config);
    }

    /**
     * 付款到银行卡
     * @param transferBank 付款到银行卡参数
     * @param wxPayV2Config 配置
     * @return 请求结果
     * @throws Exception 异常
     */
    public Map<String,String> transferBank(TransferBank transferBank,WxPayV2Config wxPayV2Config) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(transferBank);
        if (StringUtils.isEmpty(transferBank.getEnc_bank_no())){
            throw new WxPayException("银行卡号不能为空");
        }
        if (StringUtils.isEmpty(transferBank.getEnc_true_name())){
            throw new WxPayException("收款方用户名不能为空");
        }
        map.put("enc_bank_no",rsaEncryptOAEP(transferBank.getEnc_bank_no(),wxPayV2Config));
        map.put("enc_true_name",rsaEncryptOAEP(transferBank.getEnc_true_name(),wxPayV2Config));
        map.put("mch_id",wxPayV2Config.getMchId());
        map.put("nonce_str",WxPayUtil.generateNonceStr());
        String signedXml = generateSignedXml(map, wxPayV2Config);
        return carryCertificateRequestPost(wxPayV2Config,WxPayV2Content.TRANSFER_BANK_URL,signedXml);
    }


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
        map.put("mch_id",wxPayV2Config.getMchId());
        map.put("appid",wxPayV2Config.getAppId());
        String signedXml = generateSignedXml(map, wxPayV2Config);
        return carryCertificateRequestPost(wxPayV2Config,WxPayV2Content.V2QUERY_TRANSFER_URL,signedXml);
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
        map.put("mch_appid",wxPayV2Config.getAppId());
        map.put("mchid",wxPayV2Config.getMchId());
        map.put("nonce_str",WxPayUtil.generateNonceStr());
        String signedXml = generateSignedXml(map, wxPayV2Config);
        return carryCertificateRequestPost(wxPayV2Config, WxPayV2Content.V2TRANSFER_URL, signedXml);
    }

}
