import cn.itrip.common.RedisAPI;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedisAPITest {



    public void test(){
        ApplicationContext atx=new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisAPI redisAPI =(RedisAPI) atx.getBean("redisAPI");
        redisAPI.set("name3","蒋征");
        System.out.println(redisAPI.get("name3"));
    }
}
