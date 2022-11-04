package cn.qmso.wxPay.v3.pojo.only.bo.bill;

import lombok.Data;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 申请资金账单参数
 * @date Date : 2022年11月04日 10:13
 */
@Data
public class FundflowbillBo {

    /**
     * 账单日期
     * 格式yyyy-MM-dd
     * 仅支持三个月内的账单下载申请。
     * 示例值：2019-06-11
     */
    private String bill_date;

    /**
     * 资金账户类型
     * 不填则默认是BASIC
     * 枚举值：
     * BASIC：基本账户
     * OPERATION：运营账户
     * FEES：手续费账户
     * 示例值：BASIC
     */
    private String account_type;


    /**
     * 压缩类型
     * 不填则默认是数据流
     * 枚举值：
     * GZIP：返回格式为.gzip的压缩包账单
     * 示例值：GZIP
     */
    private String tar_type;
}
