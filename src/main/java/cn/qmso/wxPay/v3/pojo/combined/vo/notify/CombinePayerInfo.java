package cn.qmso.wxPay.v3.pojo.combined.vo.notify;

import lombok.Data;


/**
 * @author lijuntao
 */
@Data
public class CombinePayerInfo {
    /**
     * 使用合单appid获取的对应用户openid。是用户在商户appid下的唯一标识。
     * 示例值：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     */
    private String openid;
}