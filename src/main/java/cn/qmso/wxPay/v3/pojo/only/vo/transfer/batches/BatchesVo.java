package cn.qmso.wxPay.v3.pojo.only.vo.transfer.batches;

import lombok.Data;

import java.util.List;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 查询转账状态
 * @date Date : 2022年07月27日 15:17
 */
@Data
public class BatchesVo {

    private Integer limit;

    private Integer offset;

    /**
     * -转账批次单
     * 转账批次单基本信息
     */
    private TransferBatch transfer_batch;

    /**
     * 转账明细单列表
     * 当批次状态为“FINISHED”（已完成），且成功查询到转账明细单时返回。包括微信明细单号、明细状态信息
     */
    private List<TransferDetail> transfer_detail_list;
}
