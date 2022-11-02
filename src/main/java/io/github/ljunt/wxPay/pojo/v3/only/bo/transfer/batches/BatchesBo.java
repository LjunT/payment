package io.github.ljunt.wxPay.pojo.v3.only.bo.transfer.batches;

import lombok.Data;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 查询转账状态
 * @date Date : 2022年07月27日 15:17
 */
@Data
public class BatchesBo {

    /**
     * 是否查询转账明细单
     * 枚举值：
     * true：是；
     * false：否，默认否。
     * 商户可选择是否查询指定状态的转账明细单，当转账批次单状态为“FINISHED”（已完成）时，才会返回满足条件的转账明细单
     * 示例值：true
     */
    private Boolean need_query_detail;

    /**
     * 请求资源起始位置
     * 该次请求资源的起始位置，从0开始，默认值为0
     * 示例值：1
     */
    private int offset;

    /**
     * 最大资源条数
     * 该次请求可返回的最大明细条数，最小20条，最大100条，不传则默认20条。不足20条按实际条数返回
     * 示例值：20
     */
    private int limit;

    /**
     * 明细状态
     * query查询指定状态的转账明细单，当need_query_detail为true时，该字段必填
     * ALL:全部。需要同时查询转账成功和转账失败的明细单
     * SUCCESS:转账成功。只查询转账成功的明细单
     * FAIL:转账失败。只查询转账失败的明细单
     * 示例值：FAIL
     */
    private String detail_status;
}
