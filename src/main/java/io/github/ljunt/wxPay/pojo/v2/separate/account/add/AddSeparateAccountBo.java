/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: AddSeparateAccountBo
 * Author:   杨朝湖
 * Date:     2021/3/10 15:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package io.github.ljunt.wxPay.pojo.v2.separate.account.add;

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
public class AddSeparateAccountBo {
    /**
     * 微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 微信分配的公众账号ID
     */
    private String appid;
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