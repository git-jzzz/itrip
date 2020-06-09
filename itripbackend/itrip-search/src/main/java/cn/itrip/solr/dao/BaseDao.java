package cn.itrip.solr.dao;

import cn.itrip.beans.pojo.ItripHotel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;

public class BaseDao<T> {
    private HttpSolrClient httpSolrClient=null;

    private QueryResponse queryResponse=null;

    public BaseDao(String url){
        //创建httpSolrClient
        httpSolrClient=new HttpSolrClient(url);
        //配置解析器
        httpSolrClient.setParser(new XMLResponseParser());
        httpSolrClient.setConnectionTimeout(500);
    }

    public List<T> queryList(SolrQuery query,Class clazz) {
        SolrQuery solrQuery = new SolrQuery("*:*");//新建查询
        solrQuery.setQuery("keyword:首都");//多字段匹配
        solrQuery.setStart(0);
        solrQuery.setRows(10);
        List<T> list=null;
        try {
            queryResponse=httpSolrClient.query(query);
            list= queryResponse.getBeans(clazz);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            return list;
        }


    }
}
