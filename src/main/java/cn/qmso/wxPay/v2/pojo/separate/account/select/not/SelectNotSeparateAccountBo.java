/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SelectNotSeparateAccountBo
 * Author:   杨朝湖
 * Date:     2021/3/10 15:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v2.pojo.separate.account.select.not;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/10
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class SelectNotSeparateAccountBo {
    /**
     * 微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 微信支付订单号
     */
    private String transaction_id;
    /**
     * 随机字符串，不长于32位。推荐随机数生成算法
     */
    private String nonce_str;
    /**
     * 签名，详见签名生成算法
     */
    private String sign;
    /**
     * 签名类型，目前只支持HMAC-SHA256
     */
    private String sign_type;
}