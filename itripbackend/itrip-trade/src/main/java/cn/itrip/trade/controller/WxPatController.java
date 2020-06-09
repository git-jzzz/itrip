package cn.itrip.trade.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.common.DtoUtil;
import cn.itrip.trade.service.OrderService;
import cn.itrip.trade.service.WxMenuService;
import cn.itrip.trade.wx.WxConfig;
import cn.itrip.trade.wx.WxConstants;
import cn.itrip.trade.wx.WxUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



@RequestMapping("/api")
@Controller
public class WxPatController {
/*    http://itrip.project.bdqn.cn/trade/api/wxpay/createqccode/D1000001202006081411509fad35
    http://itrip.project.bdqn.cn/trade/api/wxpay/queryorderstatus/D1000001202006081411509fad35*/

@Autowired
OrderService orderService;

@Resource
WxMenuService wxMenuService;


    /**
     * 默认 signType 为 md5
     */
    final private String signType = WxConstants.SING_MD5;

    /**
     * 微信支付统一下单-生成二维码
     * 1.请求微信预下单接口
     * 2.根据预下单返回的 code_url 生成二维码
     * 3.将二维码 write 到前台页面
     */
    @RequestMapping(value = {"/wxpay/createqccode/{orderNo}"},method = RequestMethod.GET)
    @ResponseBody
    public Dto payUrl(HttpServletRequest request, HttpServletResponse response,

                      @PathVariable("orderNo") String orderNo
                    ) throws Exception {
        //模拟测试订单信息
        ItripHotelOrder order =orderService.loadItripHotelOrder(orderNo);
        HashMap<String, Object> result = new HashMap<String, Object>();
        order.setClintIp("123.12.12.123");

        //获取二维码链接
        String codeUrl = wxMenuService.wxPayUrl(order, signType);
        if (!StringUtils.isNotBlank(codeUrl)) {
            System.out.println("----生成二维码失败----");
            WxConfig.setPayMap(orderNo, "CODE_URL_ERROR");
            return DtoUtil.returnFail("订单支付异常", "110002");
        } else {
            result.put("hotelName", order.getHotelName());
            result.put("roomId", order.getRoomId());
            result.put("count", order.getCount());
            result.put("payAmount", order.getPayAmount());
            result.put("codeUrl", codeUrl);
            return DtoUtil.returnDataSuccess(result);

            //根据链接生成二维码
           // WxUtil.writerPayImage(response, codeUrl);
        }
    }

    /**
     * 微信支付统一下单-通知链接
     * 1.用户支付成功后
     * 2.微信回调该方法
     * 3.商户最终通知微信已经收到结果
     */
    @RequestMapping(value = {"/wxPay/unifiedorderNotify"},method = RequestMethod.POST)
    public void unifiedorderNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //商户订单号
        String outTradeNo = null;
        String transactionId=null;
        String xmlContent = "<xml>" +
                "<return_code><![CDATA[FAIL]]></return_code>" +
                "<return_msg><![CDATA[签名失败]]></return_msg>" +
                "</xml>";

        try {
            String requestXml = WxUtil.getStreamString(request.getInputStream());
            System.out.println("requestXml : " + requestXml);
            Map<String, String> map = WxUtil.xmlToMap(requestXml);
            String returnCode = map.get(WxConstants.RETURN_CODE);
            //校验一下 ，判断是否已经支付成功
            if (StringUtils.isNotBlank(returnCode) && StringUtils.equals(returnCode, "SUCCESS") && WxUtil.isSignatureValid(map, WxConfig.key, signType)) {
                //商户订单号
                outTradeNo = map.get("out_trade_no");
                System.out.println("outTradeNo : " + outTradeNo);
                //微信支付订单号
                 transactionId = map.get("transaction_id");
                System.out.println("transactionId : " + transactionId);
                //支付完成时间
                SimpleDateFormat payFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date payDate = payFormat.parse(map.get("time_end"));

                SimpleDateFormat systemFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("支付时间：" + systemFormat.format(payDate));
                //临时缓存
                WxConfig.setPayMap(outTradeNo, "SUCCESS");

                //根据支付结果修改数据库订单状态
                //其他操作

                this.orderService.paySuccess(outTradeNo,1,transactionId);
                //给微信的应答 xml, 通过 response 回写
                xmlContent = "<xml>" +
                        "<return_code><![CDATA[SUCCESS]]></return_code>" +
                        "<return_msg><![CDATA[OK]]></return_msg>" +
                        "</xml>";
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.orderService.payFailed(outTradeNo,1,transactionId);
        }
        WxUtil.responsePrint(response, xmlContent);
    }



    /**
     * 前台页面定时器查询是否已支付
     * 1.前台页面轮询
     * 2.查询订单支付状态
     */
    @RequestMapping(value = {"/wxpay/queryorderstatus/{orderNo}"},method = RequestMethod.GET)
    @ResponseBody
    public Dto payStatus(@PathVariable(value = "orderNo") String orderNo) {

        ItripHotelOrder order = null;
        try {
            order = orderService.loadItripHotelOrder(orderNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DtoUtil.returnDataSuccess(order);
    }

}
