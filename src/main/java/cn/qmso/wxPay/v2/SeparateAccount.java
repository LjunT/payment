package cn.qmso.wxPay.v2;

import cn.qmso.wxPay.base.PayV2;
import cn.qmso.wxPay.base.WxPayUtil;
import cn.qmso.wxPay.v2.pojo.separate.account.Receivers;
import cn.qmso.wxPay.v2.pojo.separate.account.SeparateAccountBo;
import cn.qmso.wxPay.v2.pojo.separate.account.add.AddSeparateAccountBo;
import cn.qmso.wxPay.v2.pojo.separate.account.delete.DeleteSeparateAccountBo;
import cn.qmso.wxPay.v2.pojo.separate.account.delete.Receiver;
import cn.qmso.wxPay.v2.pojo.separate.account.returns.ReturnSeparateAccountBo;
import cn.qmso.wxPay.v2.pojo.separate.account.select.SelectSeparateAccountBo;
import cn.qmso.wxPay.v2.pojo.separate.account.select.fallback.result.SelectFallBackResultSeparateAccountBo;
import cn.qmso.wxPay.v2.pojo.separate.account.success.SuccessSeparateAccountBo;
import cn.qmso.wxPay.base.WxPayContent;
import cn.qmso.wxPay.v2.pojo.separate.account.select.not.SelectNotSeparateAccountBo;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;


/**
 * @author lijuntao
 */
public class SeparateAccount extends PayV2 {

    private static final String url_prex = "https://api.mch.weixin.qq.com/";

    /**
     * 请求分账（单次分账，多次分账）
     * 单次分账请求按照传入的分账接收方账号和资金进行分账，同时会将订单剩余的待分账金额解冻给本商户。故操作成功后，订单不能再进行分账，也不能进行分账完结。
     * <p>
     * ● 微信订单支付成功后，商户发起分账请求，将结算后的钱分到分账接收方。多次分账请求仅会按照传入的分账接收方进行分账，不会对剩余的金额进行任何操作。故操作成功后，在待分账金额不等于零时，订单依旧能够再次进行分账。
     * <p>
     * ● 多次分账，可以将本商户作为分账接收方直接传入，实现释放资金给本商户的功能
     * <p>
     * ● 对同一笔订单最多能发起20次多次分账请求
     *
     * @param url               请求地址（单次分账，多次分账）
     * @param separateAccountBo 请求参数实体
     * @param receivers         请求参数实体集合
     * @param key               ApiV2秘钥
     * @param certPath          p12证书地址
     * @return 请求结果
     * @throws Exception 异常
     */
    public static Map<String, String> separateAccount(String url, SeparateAccountBo separateAccountBo, List<Receivers> receivers, String key, String certPath) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(separateAccountBo);
        map.put("receivers", JSONObject.toJSONString(receivers));
        String signedXml = generateSignedXml(map, key, WxPayContent.SIGN_TYPE_MD5);
        return carryCertificateRequestPost(separateAccountBo.getMch_id(), certPath, url_prex + url, signedXml);
    }

    /**
     * 添加分账用户
     * 商户发起添加分账接收方请求，后续可通过发起分账请求将结算后的钱分到该分账接收方。
     *
     * @param addSeparateAccountBo 请求参数实体
     * @param receiver             请求参数实体
     * @param key                  ApiV2秘钥
     * @return 请求结果
     * @throws Exception 异常
     */
    public static Map<String, String> addSeparateAccount(AddSeparateAccountBo addSeparateAccountBo, Receiver receiver, String key) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(addSeparateAccountBo);
        map.put("receiver", JSONObject.toJSONString(receiver));
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        Map<String, String> stringStringMap = notCarryCertificateRequestPost(addSeparateAccountBo.getMch_id(), url_prex + "pay/profitsharingaddreceiver", signedXml);
        return stringStringMap;
    }

    /**
     * 删除分账用户
     * 商户发起删除分账接收方请求，删除后不支持将结算后的钱分到该分账接收方。
     *
     * @param deleteSeparateAccountBo 请求参数实体
     * @param receiver                请求参数实体
     * @param key                     ApiV2秘钥
     * @return 请求结果
     * @throws Exception 欧昌
     */
    public static Map<String, String> deleteSeparateAccount(DeleteSeparateAccountBo deleteSeparateAccountBo, Receiver receiver, String key) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(deleteSeparateAccountBo);
        map.put("receiver", JSONObject.toJSONString(receiver));
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        Map<String, String> stringStringMap = notCarryCertificateRequestPost(deleteSeparateAccountBo.getMch_id(), url_prex + "pay/profitsharingremovereceiver", signedXml);
        return stringStringMap;
    }

    /**
     * 查询分账结果
     * 发起分账请求后，可调用此接口查询分账结果；发起分账完结请求后，可调用此接口查询分账完结的执行结果。
     *
     * @param selectSeparateAccountBo 请求参数实体
     * @param key                     ApiV2秘钥
     * @return 请求结果
     * @throws Exception 异常
     */
    public static Map<String, String> selectSeparateAccount(SelectSeparateAccountBo selectSeparateAccountBo, String key) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(selectSeparateAccountBo);
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        return notCarryCertificateRequestPost(selectSeparateAccountBo.getMch_id(), url_prex + "pay/profitsharingquery", signedXml);
    }

    /**
     * 完结分账
     * 1、不需要进行分账的订单，可直接调用本接口将订单的金额全部解冻给本商户
     * 2、调用多次分账接口后，需要解冻剩余资金时，调用本接口将剩余的分账金额全部解冻给特约商户
     * 3、已调用请求单次分账后，剩余待分账金额为零，不需要再调用此接口。
     *
     * @param successSeparateAccountBo 请求参数实体
     * @param key                      ApiV2秘钥
     * @param certPath                 p12证书地址
     * @return 请求结果
     * @throws Exception 异常
     */
    public static Map<String, String> successSeparateAccount(SuccessSeparateAccountBo successSeparateAccountBo, String key, String certPath) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(successSeparateAccountBo);
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        return carryCertificateRequestPost(successSeparateAccountBo.getMch_id(), certPath, url_prex + "secapi/pay/profitsharingfinish", signedXml);
    }

    /**
     * 查询订单待分账金额
     * 商户可通过调用此接口查询订单剩余待分金额。
     *
     * @param selectNotSeparateAccountBo 参数实体
     * @param key                        ApiV2秘钥
     * @return 请求结果
     * @throws Exception 异常
     */
    public static Map<String, String> selectNotSeparateAccount(SelectNotSeparateAccountBo selectNotSeparateAccountBo, String key) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(selectNotSeparateAccountBo);
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        return notCarryCertificateRequestPost(selectNotSeparateAccountBo.getMch_id(), url_prex + "pay/profitsharingorderamountquery", signedXml);
    }

    /**
     * 分账退回
     * ● 对订单进行退款时，如果订单已经分账，可以先调用此接口将指定的金额从分账接收方（仅限商户类型的分账接收方）回退给本商户，然后再退款。
     * <p>
     * ● 回退以原分账请求为依据，可以对分给分账接收方的金额进行多次回退，只要满足累计回退不超过该请求中分给接收方的金额。
     * <p>
     * ● 此接口采用同步处理模式，即在接收到商户请求后，会实时返回处理结果
     * <p>
     * ● 此功能需要接收方在商户平台-交易中心-分账-分账接收设置下，开启同意分账回退后，才能使用。
     *
     * @param returnSeparateAccountBo 参数实体
     * @param key                     ApiV2秘钥
     * @param certPath                p12证书地址
     * @return
     * @throws Exception
     */
    public static Map<String, String> returnSeparateAccount(ReturnSeparateAccountBo returnSeparateAccountBo, String key, String certPath) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(returnSeparateAccountBo);
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        return carryCertificateRequestPost(returnSeparateAccountBo.getMch_id(), certPath, url_prex + "secapi/pay/profitsharingreturn", signedXml);
    }

    /**
     * 回退结果查询
     * ● 商户需要核实回退结果，可调用此接口查询回退结果。
     * <p>
     * ● 如果分账回退接口返回状态为处理中，可调用此接口查询回退结果
     *
     * @param selectFallBackResultSeparateAccountBo
     * @param key
     * @return
     * @throws Exception
     */
    public static Map<String, String> selectFallBackResultSeparateAccount(SelectFallBackResultSeparateAccountBo selectFallBackResultSeparateAccountBo, String key) throws Exception {
        Map<String, String> map = WxPayUtil.objectToMap(selectFallBackResultSeparateAccountBo);
        String signedXml = generateSignedXml(map, key,WxPayContent.SIGN_TYPE_MD5);
        return notCarryCertificateRequestPost(selectFallBackResultSeparateAccountBo.getMch_id(), url_prex + "pay/profitsharingreturnquery", signedXml);
    }
}