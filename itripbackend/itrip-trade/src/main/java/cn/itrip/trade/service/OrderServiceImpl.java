package cn.itrip.trade.service;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.dao.hotel.ItripHotelMapper;
import cn.itrip.dao.hotelorder.ItripHotelOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements  OrderService {

    @Autowired
    private ItripHotelOrderMapper itripHotelOrderMapper;

    public void setItripHotelOrderMapper(ItripHotelOrderMapper itripHotelOrderMapper) {
        this.itripHotelOrderMapper = itripHotelOrderMapper;
    }

    @Override
    public ItripHotelOrder loadItripHotelOrder(String orderNo) throws Exception {
      ItripHotelOrder order=itripHotelOrderMapper.getItripHotelOrderByNo(orderNo);
        return order;
    }

    @Override
    public void paySuccess(String orderNo, int payType, String tradeNo) throws Exception {
        ItripHotelOrder order=this.loadItripHotelOrder(orderNo);
        order.setOrderStatus(2);//支付成功
        order.setPayType(payType);
        order.setTradeNo(tradeNo);
        this.itripHotelOrderMapper.updateItripHotelOrder(order);

        //减库存 有业务提供API 在这里进行调用
    }

    @Override
    public void payFailed(String orderNo, int payType, String tradeNo) throws Exception {
        ItripHotelOrder order=this.loadItripHotelOrder(orderNo);
        order.setOrderStatus(1);//支付失败
        order.setPayType(payType);
        order.setTradeNo(tradeNo);
        this.itripHotelOrderMapper.updateItripHotelOrder(order);
    }
}
