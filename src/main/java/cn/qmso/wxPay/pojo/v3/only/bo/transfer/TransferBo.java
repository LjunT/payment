package cn.qmso.wxPay.pojo.v3.only.bo.transfer;

import lombok.Data;

import java.util.List;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 商家转账到零钱参数
 * @date Date : 2022年07月26日 15:31
 */
@Data
public class TransferBo {

    /**
     * 直连商户的appid
     * 申请商户号的appid或商户号绑定的appid（企业号corpid即为此appid）
     * 示例值：wxf636efh567hg4356
     */
    private String appid;

    /**
     * 商家批次单号
     * 商户系统内部的商家批次单号，要求此参数只能由数字、大小写字母组成，在商户系统内部唯一
     * 示例值：plfk2020042013
     */
    private String out_batch_no;

    /**
     * 批次名称
     * 该笔批量转账的名称
     * 示例值：2019年1月深圳分部报销单
     */
    private String batch_name;

    /**
     * 批次备注
     * 转账说明，UTF8编码，最多允许32个字符
     * 示例值：2019年1月深圳分部报销单
     */
    private String batch_remark;

    /**
     * 转账总金额
     * 转账金额单位为“分”。转账总金额必须与批次内所有明细转账金额之和保持一致，否则无法发起转账操作
     * 示例值：4000000
     */
    private Integer total_amount;

    /**
     * 转账总笔数
     * 一个转账批次单最多发起三千笔转账。转账总笔数必须与批次内所有明细之和保持一致，否则无法发起转账操作
     * 示例值：200
     */
    private Integer total_num;

    /**
     * 转账明细列表
     * 发起批量转账的明细列表，最多三千笔
     */
    private List<TransferDetailBo> transfer_detail_list;
}
