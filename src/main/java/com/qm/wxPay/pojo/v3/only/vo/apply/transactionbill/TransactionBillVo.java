/**
 * Copyright (C), 2020-2021,贵州铭明网络科技有限公司
 * FileName: TransactionBillVo
 * Author:   杨朝湖
 * Date:     2021/3/4 13:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.qm.wxPay.pojo.v3.only.vo.apply.transactionbill;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author Gym
 * @create 2021/3/4
 * @since 1.0.0
 */
@Data
public class TransactionBillVo {
    /**
     * 原始账单（gzip需要解压缩）的摘要值，用于校验文件的完整性。
     * 示例值：SHA1
     */
    private String hash_type;
    /**
     * 原始账单（gzip需要解压缩）的摘要值，用于校验文件的完整性。
     * 示例值：79bb0f45fc4c42234a918000b2668d689e2bde04
     */
    private String hash_value;
    /**
     * 供下一步请求账单文件的下载地址，该地址30s内有效。
     * 示例值：https://api.mch.weixin.qq.com/v3/billdownload/file?token=xxx
     */
    private String download_url;
}