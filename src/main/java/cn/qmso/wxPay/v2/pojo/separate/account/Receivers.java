package cn.qmso.wxPay.v2.pojo.separate.account;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class Receivers {
    /**
     * MERCHANT_ID：商户号（mch_id或者sub_mch_id）
     * PERSONAL_OPENID：个人openid
     */
    private String type;
    /**
     * 类型是MERCHANT_ID时，是商户号（mch_id或者sub_mch_id）
     * 类型是PERSONAL_OPENID时，是个人openid
     */
    private String account;
    /**
     * 分账金额，单位为分，只能为整数，不能超过原订单支付金额及最大分账比例金额
     */
    private int amount;
    /**
     * 分账的原因描述，分账账单中需要体现
     */
    private String description;
    /**
     * 可选项，在接收方类型为个人的时可选填，若有值，会检查与 name 是否实名匹配，不匹配会拒绝分账请求
     * 1、分账接收方类型是PERSONAL_OPENID时，是个人姓名（选传，传则校验）
     */
    private String name;
}