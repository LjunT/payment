package io.github.ljunt.wxPay.pojo.v2.only.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class PlaceOrderBo {
    /**
     * 微信支付分配的公众账号ID（企业号corpid即为此appId）
     */
    private String appid;
    /**
     * 微信支付分配的商户号
     */
    private String mch_id;
    /**
     * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
     */
    private String device_info;
    /**
     * 随机字符串，长度要求在32位以内。推荐随机数生成算法
     */
    private String nonce_str;
    /**
     * 通过签名算法计算得出的签名值，详见签名生成算法
     */
    private String sign;
    /**
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
     */
    private String sign_type;
    /**
     * 商品简单描述，该字段请按照规范传递，具体请见参数规定
     */
    private String body;
    /**
     * 商品详细描述，对于使用单品优惠的商户，该字段必须按照规范上传，详见“单品优惠参数说明”
     */
    private String detail;
    /**
     * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
     */
    private String attach;
    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一。详见商户订单号
     */
    private String out_trade_no;
    /**
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
     */
    private String fee_type;
    /**
     * 订单总金额，单位为分，详见支付金额
     */
    private int total_fee;
    /**
     * 支持IPV4和IPV6两种格式的IP地址。用户的客户端IP
     */
    private String spbill_create_ip;
    /**
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     */
    private String time_start;
    /**
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id。其他详见时间规则
     * <p>
     * time_expire只能第一次下单传值，不允许二次修改，二次修改系统将报错。如用户支付失败后，需再次支付，需更换原订单号重新下单。
     * 建议：最短失效时间间隔大于1分钟
     */
    private String time_expire;
    /**
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
     */
    private String goods_tag;
    /**
     * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
     */
    private String notify_url;
    /**
     * JSAPI -JSAPI支付
     * <p>
     * NATIVE -Native支付
     * <p>
     * APP -APP支付
     * <p>
     * 说明详见参数规定
     */
    private String trade_type;
    /**
     * trade_type=NATIVE时，此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
     */
    private String product_id;
    /**
     * 上传此参数no_credit--可限制用户不能使用信用卡支付
     */
    private String limit_pay;
    /**
     * trade_type=JSAPI时（即JSAPI支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
     */
    private String openid;
    /**
     * Y，传入Y时，支付成功消息和支付详情页将出现开票入口。需要在微信支付商户平台或微信公众平台开通电子发票功能，传此字段才可生效
     */
    private String receipt;
    /**
     * Y-是，需要分账
     * N-否，不分账
     * 字母要求大写，不传默认不分账
     */
    private String profit_sharing;
}