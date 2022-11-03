package cn.qmso.wxPay.v3.pojo.only.vo.transfer.batches;

import lombok.Data;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 转账批次单基本信息
 * @date Date : 2022年07月27日 15:26
 */
@Data
public class TransferBatch {

    /**
     * 商户号
     * 微信支付分配的商户号
     * 示例值：1900001109
     */
    private String mchid;

    /**
     * 商家批次单号
     * 商户系统内部的商家批次单号，在商户系统内部唯一
     * 示例值：plfk2020042013
     */
    private String out_batch_no;

    /**
     * 微信批次单号
     * 微信批次单号，微信商家转账系统返回的唯一标识
     * 示例值：1030000071100999991182020050700019480001
     */
    private String batch_id;

    /**
     * 直连商户的appid
     * 申请商户号的appid或商户号绑定的appid（企业号corpid即为此appid）
     * 示例值：wxf636efh567hg4356
     */
    private String appid;

    /**
     * 批次状态
     * 枚举值：
     * WAIT_PAY：待付款，商户员工确认付款阶段
     * ACCEPTED:已受理。批次已受理成功，若发起批量转账的30分钟后，转账批次单仍处于该状态，可能原因是商户账户余额不足等。商户可查询账户资金流水，若该笔转账批次单的扣款已经发生，则表示批次已经进入转账中，请再次查单确认
     * PROCESSING:转账中。已开始处理批次内的转账明细单
     * FINISHED：已完成。批次内的所有转账明细单都已处理完成
     * CLOSED：已关闭。可查询具体的批次关闭原因确认
     * 示例值：ACCEPTED
     */
    private String batch_status;

    /**
     * 批次类型.
     * 枚举值：
     * API：API方式发起
     * WEB：页面方式发起
     * 示例值：API
     */
    private String batch_type;

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
     * 批次关闭原因
     * 如果批次单状态为“CLOSED”（已关闭），则有关闭原因
     * MERCHANT_REVOCATION：商户主动撤销
     * OVERDUE_CLOSE：系统超时关闭
     * 示例值：OVERDUE_CLOSE
     */
    private String close_reason;

    /**
     * 转账总金额
     * 转账金额单位为分
     * 示例值：4000000
     */
    private int total_amount;

    /**
     * 转账总笔数
     * 一个转账批次单最多发起三千笔转账
     * 示例值：200
     */
    private int total_num;

    /**
     * 批次创建时间
     * 批次受理成功时返回，遵循rfc3339标准格式，格式为yyyy-MM-DDTHH:mm:ss.sss+TIMEZONE，yyyy-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss.sss表示时分秒毫秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35.120+08:00表示北京时间2015年05月20日13点29分35秒
     * 示例值：2015-05-20T13:29:35.120+08:00
     */
    private String create_time;

    /**
     * 批次更新时间
     * 批次最近一次状态变更的时间，遵循rfc3339标准格式，格式为yyyy-MM-DDTHH:mm:ss.sss+TIMEZONE，yyyy-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss.sss表示时分秒毫秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35.120+08:00表示北京时间2015年05月20日13点29分35秒
     * 示例值：2015-05-20T13:29:35.120+08:00
     */
    private String update_time;

    /**
     * 转账成功金额
     * 转账成功的金额，单位为分。当批次状态为“PROCESSING”（转账中）时，转账成功金额随时可能变化
     * 示例值：3900000
     */
    private int success_amount;

    /**
     * 转账成功笔数
     * 转账成功的笔数。当批次状态为“PROCESSING”（转账中）时，转账成功笔数随时可能变化
     * 示例值：199
     */
    private int success_num;

    /**
     * 转账失败金额
     * 转账失败的金额，单位为分
     * 示例值：100000
     */
    private int fail_amount;

    /**
     * 转账失败笔数
     * 转账失败的笔数
     * 示例值：1
     */
    private int fail_num;

    /**
     * 微信返回参数 文档中不存在
     */
    private String transfer_scene_id;
}
