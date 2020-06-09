package cn.itrip.trade.wx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author zhangye
 * @version 1.0
 * @description 微信公众号开发配置类
 * @date 2019/12/19
 */
@Component
public class WxConfig {

    /**
     * 开发者ID
     */
    public static String appID = "wxab8acb865bb1637e";

    /**
     * 开发者密码
     */
    public static String appSecret = "86ae4a77893342f7568947e243c84d9aa";

    /**
     * 商户号
     */
    public static String mchID = "11473623";


    /**
     * API密钥
     */
    public static String key= "2ab9071b06b9f739b950ddb41db2690d";


    /**
     * 统一下单-通知链接
     */
    public static String unifiedorderNotifyUrl="http://vaiiak.wezoz.com/trade/api/wxPay/unifiedorderNotify";

    /**
     * 连接超时时间
     */
    public static Integer connectionTimeout=15000;

    /**
     * 连接超时时间
     */
    public static Integer readTimeout=15000;

    //支付map缓存处理
    private static HashMap<String,String> payMap = new HashMap<String,String>();
    public static String getPayMap(String key) {
        return payMap.get(key);
    }
    public static void setPayMap(String key,String value) {
        payMap.put(key,value);
    }


}
