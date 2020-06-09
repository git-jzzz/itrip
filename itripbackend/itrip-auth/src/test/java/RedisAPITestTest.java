import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.RedisAPI;
import cn.itrip.service.UserService;
import org.apache.http.HttpResponse;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class RedisAPITestTest {


    public void test1() {
        ApplicationContext atx=new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisAPI redisAPI =(RedisAPI) atx.getBean("redisAPI");
        redisAPI.set("name3","蒋征");
        System.out.println(redisAPI.get("name3"));
    }

    //邮箱注册

    public void testCreateUser(){
        ApplicationContext atx=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService=(UserService) atx.getBean("userService");
        ItripUser user=new ItripUser();
        user.setUserCode("771953496@qq.com");
        user.setUserName("test");
        try {
            userService.itriptxCreateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void testActivateUser(){
        ApplicationContext atx=new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService=(UserService) atx.getBean("userService");
        try {
            userService.activate("771953496@qq.com","ea503f85919d3da21878ff9989f7b597");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, SolrServerException {
       /* //连接URL
        String url="http://localhost:8080/solr/test/";//本地solr访问地址 ip:端口号/solr/模块名
        HttpSolrClient httpSolrClient=new HttpSolrClient(url);//执行http请求的客户端
        httpSolrClient.setParser(new XMLResponseParser());//设置响应解析器
        httpSolrClient.setConnectionTimeout(500);//建立连接的最长时间
        SolrQuery  solrQuery=new SolrQuery("*:*");//新建查询
        solrQuery.setQuery("keyword:itrip");
        solrQuery.setStart(0);
        solrQuery.setRows(10);
        QueryResponse queryResponse=httpSolrClient.query(solrQuery);
        List<ItripUser> userList=queryResponse.getBeans(ItripUser.class);
        for(ItripUser user:userList){
            System.out.print(user.getUserCode());
        }
        */
        String url="http://localhost:8080/solr/hotel/";//本地solr访问地址
        HttpSolrClient httpSolrClient=new HttpSolrClient(url);//执行请求的客户端
        httpSolrClient.setParser(new XMLResponseParser());//设置响应解析器
        httpSolrClient.setConnectionTimeout(500);
        SolrQuery solrQuery=new SolrQuery("*:*");//新建查询
        solrQuery.setQuery("keyword:首都");//多字段匹配
        solrQuery.setStart(0);
        solrQuery.setRows(10);
        QueryResponse queryResponse=httpSolrClient.query(solrQuery);
        List<ItripHotel> hotelList=queryResponse.getBeans(ItripHotel.class);
        for(ItripHotel hotel:hotelList){
            System.out.print("酒店名:"+hotel.getHotelName()+"\t酒店地址:"+hotel.getAddress()+"\n");
        }
    }

}