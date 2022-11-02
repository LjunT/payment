/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: WxComBinedPayV3Util
 * Author:   杨朝湖
 * Date:     2021/3/4 13:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package io.github.ljunt.wxPay.v3;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ljunt.wxPay.base.Pay;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.ljunt.wxPay.pojo.v2.only.close.CloseOrderBo;
import io.github.ljunt.wxPay.pojo.v3.combined.bo.placeorder.CombinedPayBo;
import io.github.ljunt.wxPay.pojo.v3.combined.vo.notify.CombinedNotfyVo;
import io.github.ljunt.wxPay.pojo.v3.combined.vo.selectorder.SelectComBinedOrderVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话功能简述〉<br>
 * 〈微信支付V3版本合单支付〉
 *
 * @author Gym
 * @create 2021/3/4
 * @since 1.0.0
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
        Object body = postRequest(WxPayV3Content.URL_PRE, url, mchId, serialNo,null, privateKeyFilePath, JSONUtil.toJsonStr(combinedPayBo));
        switch (url) {
            case "v3/combine-transactions/app":
            case "v3/combine-transactions/jsapi":
                return JSONObject.fromObject(body).getString("prepay_id");
            case "v3/combine-transactions/native":
                return JSONObject.fromObject(body).getString("code_url");
            case "v3/combine-transactions/h5":
                return JSONObject.fromObject(body).getString("h5_url");
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
        Object body = postRequest(WxPayV3Content.URL_PRE, url, mchid, serial_no,null, privateKeyFilePath, JSONUtil.toJsonStr(closeOrderBo));
        return body.toString();
    }

}