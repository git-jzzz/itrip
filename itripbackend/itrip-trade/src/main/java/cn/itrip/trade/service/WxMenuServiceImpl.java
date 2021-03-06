package cn.itrip.trade.service;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.trade.wx.HttpsClient;
import cn.itrip.trade.wx.WxConfig;
import cn.itrip.trade.wx.WxConstants;
import cn.itrip.trade.wx.WxUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangye
 * @version 1.0
 * @description 微信支付实现类
 * @date 2019/12/19
 */
@Service("wxMenuService")
public class WxMenuServiceImpl implements WxMenuService {

    @Override
    public String wxPayUrl(ItripHotelOrder order, String signType) throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        //公众账号ID
        data.put("appid", WxConfig.appID);
        //商户号
        data.put("mch_id", WxConfig.mchID);
        //随机字符串
        data.put("nonce_str", WxUtil.getNonceStr());
        //商品描述
        data.put("body", order.getHotelName()+"   房间数:"+order.getCount());
        //商户订单号
        data.put("out_trade_no", order.getOrderNo());
        //标价币种
        data.put("fee_type", "CNY");

        //(Math.Round((decimal)order.getPayAmount() * 100, 0)).ToString()  元转分
        //标价金额
        data.put("total_fee", "1");
        //用户的IP
        data.put("spbill_create_ip", order.getClintIp());
        //        //通知地址
        data.put("notify_url", WxConfig.unifiedorderNotifyUrl);
        //交易类型
        data.put("trade_type", "NATIVE");
        //签名类型
        data.put("sign_type", signType);
        //商品id
        data.put("product_id", String.valueOf(order.getHotelId()));
        System.out.println(WxConfig.key);
        //签名 签名中加入key
        data.put("sign", WxUtil.getSignature(data, WxConfig.key, signType));

        String requestXML = WxUtil.mapToXml(data);
        String responseString = HttpsClient.httpsRequestReturnString(WxConstants.PAY_UNIFIEDORDER, HttpsClient.METHOD_POST, requestXML);
        //解析返回的xml
        Map<String, String> resultMap = WxUtil.processResponseXml(responseString, signType);
        if (resultMap.get(WxConstants.RETURN_CODE).equals("SUCCESS")) {
            return resultMap.get("code_url");
        }
        return null;
    }

    @Override
    public String wxOrderQuery(String orderNo, String signType) throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        //公众账号ID
        data.put("appid", WxConfig.appID);
        //商户号
        data.put("mch_id", WxConfig.mchID);
        //随机字符串
        data.put("nonce_str", WxUtil.getNonceStr());
        //商户订单号
        data.put("out_trade_no", orderNo);
        //签名类型
        data.put("sign_type", signType);
        //签名 签名中加入key
        data.put("sign", WxUtil.getSignature(data, WxConfig.key, signType));
        String requestXML = WxUtil.mapToXml(data);
        String responseString = HttpsClient.httpsRequestReturnString(WxConstants.PAY_ORDERQUERY, HttpsClient.METHOD_POST, requestXML);
        //解析返回的xml
        Map<String, String> resultMap = WxUtil.processResponseXml(responseString, signType);
        if (resultMap.get(WxConstants.RETURN_CODE).equals("SUCCESS")) {
            /**
             * 订单支付状态
             * SUCCESS—支付成功
             * REFUND—转入退款
             * NOTPAY—未支付
             * CLOSED—已关闭
             * REVOKED—已撤销（刷卡支付）
             * USERPAYING--用户支付中
             * PAYERROR--支付失败(其他原因，如银行返回失败)
             */
            return resultMap.get("trade_state");
        }
        return null;
    }

    @Override
    public String wxCloseOrder(String orderNo, String signType) throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        //公众账号ID
        data.put("appid", WxConfig.appID);
        //商户号
        data.put("mch_id", WxConfig.mchID);
        //随机字符串
        data.put("nonce_str", WxUtil.getNonceStr());
        //商户订单号
        data.put("out_trade_no", orderNo);
        //签名类型
        data.put("sign_type", signType);
        //签名 签名中加入key
        data.put("sign", WxUtil.getSignature(data, WxConfig.key, signType));
        String requestXML = WxUtil.mapToXml(data);
        String responseString = HttpsClient.httpsRequestReturnString(WxConstants.PAY_CLOSEORDER, HttpsClient.METHOD_POST, requestXML);
        //解析返回的xml
        Map<String, String> resultMap = WxUtil.processResponseXml(responseString, signType);
        if (resultMap.get(WxConstants.RETURN_CODE).equals("SUCCESS")) {
            /**
             * 关闭订单状态
             * SUCCESS—关闭成功
             * FAIL—关闭失败
             */
            return resultMap.get("result_code");
        }
        return null;
    }

}
