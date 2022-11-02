package cn.qmso.wxPay.v3;

import cn.hutool.json.JSONUtil;
import cn.qmso.wxPay.base.Pay;
import cn.qmso.wxPay.pojo.v3.only.bo.transfer.TransferBo;
import cn.qmso.wxPay.pojo.v3.only.bo.transfer.TransferDetailBo;
import cn.qmso.wxPay.pojo.v3.only.vo.transfer.TransferVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import cn.qmso.wxPay.pojo.v3.only.bo.transfer.batches.BatchesBo;
import cn.qmso.wxPay.pojo.v3.only.vo.transfer.batches.BatchesVo;

import java.security.cert.Certificate;
import java.util.Map;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 微信商家转账接口
 * @date Date : 2022年07月26日 15:28
 */
@Slf4j
public class WxTransferV3Util extends Pay {


    /**
     * 查询商家转账到零钱结果
     * @param batchesBo 查询参数
     * @param mchid 商户号
     * @param serial_no 证书序列号
     * @param privateKeyFilePath 证书地址
     * @return 查询结果
     * @throws Exception 异常
     */
    public static BatchesVo selectTransfer(BatchesBo batchesBo, String batchId, String mchid, String serial_no, String privateKeyFilePath) throws Exception {
        Object body = getRequest(WxPayV3Content.URL_PRE, String.format(WxPayV3Content.V3_TRANSFER_SELECT,batchId),batchesBo, mchid, serial_no, privateKeyFilePath);
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
     * @param mchid 商户号
     * @param serial_no api证书序列号
     * @param privateKeyFilePath pem证书地址
     * @param v3Key v3key
     * @return 转账信息
     * @throws Exception 异常
     */
    public static TransferVo transfer(TransferBo transferBo, String mchid, String serial_no, String privateKeyFilePath, String v3Key) throws Exception {
        Map<String, Certificate> mapCertificate = getCertificate(WxPayV3Content.URL_PRE, WxPayV3Content.V3_CERTIFICATES_URL, mchid, serial_no, privateKeyFilePath, v3Key);
        String platformSerialNo = null;
        Certificate certificate = null;
        for (Map.Entry<String, Certificate> stringCertificateEntry : mapCertificate.entrySet()) {
            platformSerialNo = stringCertificateEntry.getKey();
            certificate = stringCertificateEntry.getValue();
        }
        for (TransferDetailBo transferDetailBo : transferBo.getTransfer_detail_list()) {
            if (StringUtils.isNotEmpty(transferDetailBo.getUser_name())){
                assert certificate != null;
                String encryptOAEP = rsaEncryptOAEP(transferDetailBo.getUser_name(), certificate.getPublicKey());
                transferDetailBo.setUser_name(encryptOAEP);
            }

        }

        Object body = postRequest(WxPayV3Content.URL_PRE , WxPayV3Content.V3_TRANSFER_BATCHES, mchid, serial_no,platformSerialNo, privateKeyFilePath, JSONUtil.toJsonStr(transferBo));
        TransferVo transferVo = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            transferVo = mapper.readValue(body.toString(),
                    new TypeReference<TransferVo>() {
                    });
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if (transferVo == null){
            transferVo = new TransferVo();
            transferVo.setMessage(JSONObject.fromObject(body).getString("message"));
        }
        return transferVo;
    }


}
