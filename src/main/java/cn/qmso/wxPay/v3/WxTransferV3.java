package cn.qmso.wxPay.v3;

import cn.qmso.wxPay.base.Pay;
import cn.qmso.wxPay.v3.config.WxPayV3Config;
import cn.qmso.wxPay.v3.pojo.only.bo.transfer.TransferBo;
import cn.qmso.wxPay.v3.pojo.only.bo.transfer.TransferDetailBo;
import cn.qmso.wxPay.v3.pojo.only.vo.transfer.TransferVo;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import cn.qmso.wxPay.v3.pojo.only.bo.transfer.batches.BatchesBo;
import cn.qmso.wxPay.v3.pojo.only.vo.transfer.batches.BatchesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.cert.Certificate;
import java.util.Map;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 微信商家转账接口
 * @date Date : 2022年07月26日 15:28
 */
@Slf4j
@Component
public class WxTransferV3 extends Pay {

    @Autowired
    private WxPayV3Config defaultWxPayV3Config;


    /**
     * 查询商家转账到零钱结果
     * @param batchesBo 查询参数
     * @param batchId 微信批次单号
     * @return 查询结果
     * @throws Exception 异常
     */
    public BatchesVo selectTransfer(BatchesBo batchesBo, String batchId) throws Exception {
        return selectTransfer(batchesBo,batchId,defaultWxPayV3Config);
    }

    /**
     * 查询商家转账到零钱结果
     * @param batchesBo 查询参数
     * @param batchId 微信批次单号
     * @param wxPayV3Config 配置信息
     * @return 查询结果
     * @throws Exception 异常
     */
    public BatchesVo selectTransfer(BatchesBo batchesBo, String batchId, WxPayV3Config wxPayV3Config) throws Exception {
        Object body = getRequest(WxPayV3Content.URL_PRE + String.format(WxPayV3Content.V3_TRANSFER_SELECT,batchId),
                batchesBo,
                wxPayV3Config);
        BatchesVo batchesVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            batchesVo = mapper.readValue(body.toString(),
                    new TypeReference<BatchesVo>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batchesVo;
    }

    /**
     * 发起商家转账到零钱
     * @param transferBo 转账信息
     * @return 转账信息
     * @throws Exception 异常
     */
    public TransferVo transfer(TransferBo transferBo) throws Exception {
        return transfer(transferBo,defaultWxPayV3Config);
    }

    /**
     * 发起商家转账到零钱
     * @param transferBo 转账信息
     * @param wxPayV3Config 配置信息
     * @return 转账信息
     * @throws Exception 异常
     */
    public TransferVo transfer(TransferBo transferBo, WxPayV3Config wxPayV3Config) throws Exception {
        Map<String, Certificate> mapCertificate = getCertificate(WxPayV3Content.URL_PRE + WxPayV3Content.V3_CERTIFICATES_URL, wxPayV3Config);
        String platformSerialNo = null;
        Certificate certificate = null;
        for (Map.Entry<String, Certificate> stringCertificateEntry : mapCertificate.entrySet()) {
            platformSerialNo = stringCertificateEntry.getKey();
            certificate = stringCertificateEntry.getValue();
        }
        for (TransferDetailBo transferDetailBo : transferBo.getTransfer_detail_list()) {
            if (StringUtils.isNotEmpty(transferDetailBo.getUser_name())){
                assert certificate != null;
                String encrypt = rsaEncryptOAEP(transferDetailBo.getUser_name(), certificate.getPublicKey());
                transferDetailBo.setUser_name(encrypt);
            }

        }

        String body = postRequest(WxPayV3Content.URL_PRE + WxPayV3Content.V3_TRANSFER_BATCHES,
                platformSerialNo,
                JSONObject.toJSONString(transferBo),
                wxPayV3Config);
        TransferVo transferVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            transferVo = mapper.readValue(body,
                    new TypeReference<TransferVo>() {
                    });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (transferVo == null){
            transferVo = new TransferVo();
            transferVo.setMessage(JSONObject.parseObject(body).getString("message"));
        }
        return transferVo;
    }


}
