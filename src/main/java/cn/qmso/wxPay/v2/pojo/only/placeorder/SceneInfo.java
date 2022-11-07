package cn.qmso.wxPay.v2.pojo.only.placeorder;

import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author lijuntao
 */
@Data
@Accessors(chain = true)
public class SceneInfo {
    /**
     * 门店编号，由商户自定义
     */
    private String id;
    /**
     * 门店名称 ，由商户自定义
     */
    private String name;
    /**
     * 门店所在地行政区划码，详细见《最新县及县以上行政区划代码》
     */
    private String area_code;
    /**
     * 门店详细地址 ，由商户自定义
     */
    private String address;
}