package com.qm.wxPay.pojo.v3.only.vo.transfer.batches;

import lombok.Data;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 转账明细单列表
 * @date Date : 2022年07月27日 15:32
 */
@Data
public class TransferDetail {

    /**
     * 微信明细单号
     * 微信支付系统内部区分转账批次单下不同转账明细单的唯一标识
     * 示例值：1040000071100999991182020050700019500100
     */
    private String detail_id;

    /**
     * 商家明细单号
     * 商户系统内部区分转账批次单下不同转账明细单的唯一标识
     * 示例值：x23zy545Bd5436
     */
    private String out_detail_no;

    /**
     * 明细状态
     * 枚举值：
     * PROCESSING：转账中。正在处理中，转账结果尚未明确
     * SUCCESS：转账成功
     * FAIL：转账失败。需要确认失败原因后，再决定是否重新发起对该笔明细单的转账（并非整个转账批次单）
     * 示例值：SUCCESS
     */
    private String detail_status;
}
