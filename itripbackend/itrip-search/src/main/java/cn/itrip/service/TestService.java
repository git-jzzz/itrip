package cn.itrip.service;

import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.common.Page;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface TestService {
    /**
     * 多字段匹配查询 分页
     * @param keyword
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    public List<ItripHotel> queryHotelList(String keyword,String redundantCityName,Page page,Integer level) throws IOException, SolrServerException;

    public List<ItripHotel> queryHotelByCity(int cityId,int count) throws IOException, SolrServerException;

    /**
            * 查询总页数
     * @param keyword
     * @return
             */
    public int queryCount(String keyword,String redundantCityName,Integer level) throws IOException, SolrServerException;
}
