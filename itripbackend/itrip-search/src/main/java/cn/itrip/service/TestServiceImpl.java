package cn.itrip.service;

import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.common.Page;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



@Service("testService")
public class TestServiceImpl implements  TestService {

    @Autowired
    private HttpSolrClient httpSolrClient;//注入solrbean

    public void setHttpSolrClient(HttpSolrClient httpSolrClient) {
        this.httpSolrClient = httpSolrClient;
    }

    @Override
    public List<ItripHotel> queryHotelByCity(int cityId, int count) throws IOException, SolrServerException {
        SolrQuery query=new SolrQuery("cityId:"+cityId);
        query.setStart(0);
        query.setRows(count);
        QueryResponse response = httpSolrClient.query(query);
        List<ItripHotel> comList=response.getBeans(ItripHotel.class);
        return comList;
    }

    @Override
    public List<ItripHotel> queryHotelList(String keyword,String redundantCityName,Page page,Integer level) throws IOException, SolrServerException {

        SolrQuery query=new SolrQuery("redundantCityName:"+redundantCityName);
        if(level!=null){
            query.addFilterQuery("hotelLevel:"+level);
        }
        if(!keyword.equals("")){
            query.setQuery("keyword:"+keyword);
        }

        //1、过滤器
        //query.set("fq","infoPrice:[1 TO 1000]");
        //2、排序
        //query.set("sort","infoPrice desc","id asc");
        //3、设置查询到的文档返回的域对象
      /*  query.set("fi","id,infoName,infoArtist,infoAddress");*/

        //4、设置默认查询的域
       /* query.set("df","infoName","infoArtist","infoAddress");*/

        //5.分页
        query.set("start",(page.getCurPage()-1)*page.getPageSize());
        query.set("rows",page.getPageSize());

        QueryResponse response = httpSolrClient.query(query);

        //普通查询
       /* SolrDocumentList results = response.getResults();*/
        List<ItripHotel> comList=response.getBeans(ItripHotel.class);
        return comList;
    }



    @Override
    public int queryCount(String keyword,String redundantCityName,Integer level) throws IOException, SolrServerException {
        SolrQuery query=new SolrQuery("redundantCityName:"+redundantCityName);
        if(level!=null){
            query.addFilterQuery("hotelLevel:"+level);
        }
        if(!keyword.equals("")){
            query.setQuery("keyword:"+keyword);
        }
        query.setStart(0);
        query.setRows(200);
        QueryResponse response = httpSolrClient.query(query);
    List<ItripHotel> list=response.getBeans(ItripHotel.class);
        return list.size();
    }
}
