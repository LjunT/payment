package cn.qmso.wxPay.pojo.v3.only.vo.transfer;

import lombok.Data;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 商家转账返回数据
 * @date Date : 2022年07月26日 15:39
 */
@Data
public class TransferVo {

    /**
     *商家批次单号
     * 商户系统内部的商家批次单号
     * 示例值：plfk2020042013
     */
    private String out_batch_no;

    /**
     *微信批次单号
     * 微信批次单号，微信商家转账系统返回的唯一标识
     * 示例值：1030000071100999991182020050700019480001
     */
    private String batch_id;

    /**
     * 批次创建时间
     * 批次受理成功时返回，遵循rfc3339标准格式，格式为yyyy-MM-DDTHH:mm:ss.sss+TIMEZONE，yyyy-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss.sss表示时分秒毫秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35.120+08:00表示北京时间2015年05月20日13点29分35秒
     * 示例值：2015-05-20T13:29:35.120+08:00
     */
    private String create_time;

    /**
     * 微信转账失败原因
     */
    private String message;
}
