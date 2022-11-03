/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: SelectSeparateAccountBo
 * Author:   杨朝湖
 * Date:     2021/3/10 15:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package cn.qmso.wxPay.v2.pojo.separate.account.select;

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
public class SelectSeparateAccountBo {
    /**
     * 微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 微信支付订单号
     */
    private String transaction_id;
    /**
     * 查询分账结果，输入申请分账时的商户分账单号； 查询分账完结执行的结果，输入发起分账完结时的商户分账单号
     */
    private String out_order_no;
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