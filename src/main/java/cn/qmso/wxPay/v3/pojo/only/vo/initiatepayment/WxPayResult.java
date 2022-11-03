/**
 * Copyright (C), 2020-2020,贵州铭明网络科技有限公司
 * FileName: WxPayResult
 * Author:   杨朝湖
 * Date:     2020/12/16 12:48
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v3.pojo.only.vo.initiatepayment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈一句话功能简述〉<br>
 * 〈微信调起返回参数〉
 *
 * @author Gym
 * @create 2020/12/16
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class WxPayResult {
    /**
     * APPId
     */
    private String appid;
    /**
     * 下单时间
     */
    private String timeStamp;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 包名
     */
    private String packages;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 支付签名
     */
    private String paySign;

    public WxPayResult(String appid, String timeStamp, String nonceStr, String packages, String signType, String paySign) {
        this.appid = appid;
        this.timeStamp = timeStamp;
        this.nonceStr = nonceStr;
        this.packages = packages;
        this.signType = signType;
        this.paySign = paySign;
    }
}