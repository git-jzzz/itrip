package cn.itrip.solr.service;

import cn.itrip.beans.pojo.ItripHotel;

import java.util.List;

public interface HotelService {
    public List<ItripHotel> queryHotelList(String keyword);
}
