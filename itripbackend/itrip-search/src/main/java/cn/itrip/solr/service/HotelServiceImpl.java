package cn.itrip.solr.service;

import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.solr.dao.HotelDao;
import cn.itrip.solr.dao.HotelDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("hotelService")
public class HotelServiceImpl implements  HotelService {


    private HotelDao hotelDao=new HotelDaoImpl();

    @Override
    public List<ItripHotel> queryHotelList(String keyword) {
        return hotelDao.queryHotelList("keyword:"+keyword);
    }
}
