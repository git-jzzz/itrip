package cn.itrip;

import cn.itrip.pojo.Hotel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;

public class Test {
    static String url = "http://localhost:8080/solr/hotel";

    public static void main(String[] args) {
        //初始化HttpSolrClient
        HttpSolrClient httpSolrClient = new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser()); // 设置响应解析器
        //初始化SolrQuery
        SolrQuery query = new SolrQuery("cityId:2");
        query.setStart(0);
        //一页显示多少条
        query.setRows(10);
        QueryResponse queryResponse = null;
        try {
            queryResponse = httpSolrClient.query(query);
            List<Hotel> list = queryResponse.getBeans(Hotel.class);
            for (Hotel hotel : list) {
                System.out.println(hotel);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
