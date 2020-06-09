package cn.itrip.solr.dao;

import cn.itrip.beans.pojo.ItripHotel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.zookeeper.data.Stat;

import java.util.List;

public class HotelDaoImpl implements  HotelDao {

    private static String url="http://localhost:8080/solr/hotel";

    private  BaseDao<ItripHotel> hotelBaseDao=new BaseDao<ItripHotel>(url);

    @Override
    public List<ItripHotel> queryHotelList(String keyword) {
        SolrQuery solrQuery=new SolrQuery(keyword);
        return hotelBaseDao.queryList(solrQuery,ItripHotel.class);
    }
}
