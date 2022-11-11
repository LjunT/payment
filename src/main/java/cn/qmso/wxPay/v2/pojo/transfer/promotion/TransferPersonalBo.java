package cn.qmso.wxPay.v2.pojo.transfer.promotion;

import lombok.Data;

/**
 * @author : ljt
 * @version V1.0
 * @Description: 企业付款到零钱参数
 * @date Date : 2022年11月10日 17:54
 */
@Data
public class TransferPersonalBo {

    /**
     * 设备号
     * 微信支付分配的终端设备号
     */
    private String device_info;

    /**
     * 商户订单号
     * 商户订单号，需保持唯一性
     * (只能是字母或者数字，不能包含有其它字符)
     */
    private String partner_trade_no;

    /**
     * 用户openid
     * openid是微信用户在公众账号下的唯一用户标识（appid不同，则获取到的openid就不同），可用于永久标记一个用户。
     */
    private String openid;

    /**
     * 校验用户姓名选项
     * NO_CHECK：不校验真实姓名
     * FORCE_CHECK：强校验真实姓名
     */
    private String check_name;

    /**
     * 收款用户姓名
     * 收款用户真实姓名。
     * 如果check_name设置为FORCE_CHECK，则必填用户真实姓名；
     * 如需电子回单，需要传入收款用户姓名。
     * 商户需确保向微信支付传输用户身份信息和账号标识信息做一致性校验已合法征得用户授权。
     */
    private String re_user_name;

    /**
     * 金额
     * 付款金额，单位为分
     */
    private int amount;

    /**
     * 付款备注
     * 付款备注，必填。
     */
    private String desc;

    /**
     * Ip地址
     * 该IP同在商户平台设置的IP白名单中的IP没有关联，该IP可传用户端或者服务端的IP。
     */
    private String spbill_create_ip;

    /**
     * 付款场景
     * BRAND_REDPACKET：品牌红包，
     * 其他值或不传则默认为普通付款到零钱
     * （品牌红包能力暂未全量开放，若有意愿参与内测请填写问卷https://wj.qq.com/s2/9229085/29f4/）
     */
    private String scene;

    /**
     * 品牌ID
     * 品牌在微信支付的唯一标识。仅在付款场景为品牌红包时必填
     */
    private Integer brand_id;

    /**
     * 消息模板ID
     * 品牌所配置的消息模板的唯一标识。仅在付款场景为品牌红包时必填。
     */
    private String finder_template_id;
}
