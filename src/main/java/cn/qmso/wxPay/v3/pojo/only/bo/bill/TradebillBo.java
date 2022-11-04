package cn.qmso.wxPay.v3.pojo.only.bo.bill;

import lombok.Data;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 申请交易账单参数
 * @date Date : 2022年11月04日 10:07
 */
@Data
public class TradebillBo {


    /**
     * 账单日期
     * 格式yyyy-MM-dd
     * 仅支持三个月内的账单下载申请。
     * 示例值：2019-06-11
     */
    private String bill_date;


    /**
     * 账单类型
     * 不填则默认是ALL
     * 枚举值：
     * ALL：返回当日所有订单信息（不含充值退款订单）
     * SUCCESS：返回当日成功支付的订单（不含充值退款订单）
     * REFUND：返回当日退款订单（不含充值退款订单）
     * 示例值：ALL
     */
    private String bill_type;

    /**
     * 压缩类型
     *  不填则默认是数据流
     * 枚举值：
     * GZIP：返回格式为.gzip的压缩包账单
     * 示例值：GZIP
     */
    private String tar_type;
}
