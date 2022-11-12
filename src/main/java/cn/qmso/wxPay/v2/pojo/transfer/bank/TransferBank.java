package cn.qmso.wxPay.v2.pojo.transfer.bank;

import lombok.Data;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 付款到银行卡请求参数
 * @date Date : 2022年11月12日 11:37
 */
@Data
public class TransferBank {

    /**
     * 商户付款单号
     * 商户订单号，需保持唯一（只允许数字[0~9]或字母[A~Z]和[a~z]，最短8位，最长32位）
     */
    private String partner_trade_no;

    /**
     * 收款方银行卡号
     * 商户需确保收集用户的银行卡信息，以及向微信支付传输用户姓名和账号标识信息做一致性校验已合法征得用户授权。
     */
    private String enc_bank_no;

    /**
     * 收款方用户名
     * 收款方用户名（采用标准RSA算法，公钥由微信侧提供）详见获取RSA加密公钥API；
     * 商户需确保收集用户的姓名信息，以及向微信支付传输用户姓名和账号标识信息做一致性校验已合法征得用户授权
     */
    private String enc_true_name;

    /**
     * 收款方开户行
     * 银行卡所在开户行编号,详见银行编号列表
     */
    private String bank_code;

    /**
     * 付款金额
     * 付款金额：RMB分（支付总额，不含手续费）
     * 注：大于0的整数
     */
    private int amount;

    /**
     * 付款说明
     * 付款到银行卡付款说明,即订单备注（UTF8编码，允许100个字符以内）
     */
    private String desc;
}
