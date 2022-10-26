package com.qm.wxPay.pojo.v3.only.bo.transfer;

import lombok.Data;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 转账明细
 * @date Date : 2022年07月26日 15:36
 */
@Data
public class TransferDetailBo {

    /**
     * 商家明细单号
     * 商户系统内部区分转账批次单下不同转账明细单的唯一标识，要求此参数只能由数字、大小写字母组成
     * 示例值：x23zy545Bd5436
     */
    private String out_detail_no;

    /**
     * 转账金额
     * 转账金额单位为分
     * 示例值：200000
     */
    private Integer transfer_amount;

    /**
     * 转账备注
     * 单条转账备注（微信用户会收到该备注），UTF8编码，最多允许32个字符
     * 示例值：2020年4月报销
     */
    private String 	transfer_remark;

    /**
     * 用户在直连商户应用下的用户标示
     * openid是微信用户在公众号appid下的唯一用户标识（appid不同，则获取到的openid就不同），可用于永久标记一个用户
     * 获取openid
     * 示例值：o-MYE42l80oelYMDE34nYD456Xoy
     */
    private String openid;

    /**
     * 收款用户姓名
     * 1、明细转账金额 >= 2,000，收款用户姓名必填；
     * 2、同一批次转账明细中，收款用户姓名字段需全部填写、或全部不填写；
     * 3、 若传入收款用户姓名，微信支付会校验用户openID与姓名是否一致，并提供电子回单；
     * 4、收款方姓名。采用标准RSA算法，公钥由微信侧提供
     * 5、该字段需进行加密处理，加密方法详见敏感信息加密说明。(提醒：必须在HTTP头中上送Wechatpay-Serial)
     * 6、商户需确保收集用户的姓名信息，以及向微信支付传输用户姓名和账号标识信息做一致性校验已合法征得用户授权
     * 示例值：757b340b45ebef5467rter35gf464344v3542sdf4t6re4tb4f54ty45t4yyry45
     */
    private String user_name;
}
