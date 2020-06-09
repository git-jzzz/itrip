package cn.itrip.solr.dao;

import cn.itrip.beans.pojo.ItripHotel;

import java.util.List;

public interface HotelDao {
    public List<ItripHotel> queryHotelList(String keyword);
}
