package cn.itrip.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import com.alibaba.fastjson.JSON;
import net.bytebuddy.description.annotation.AnnotationValue;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisAPI redisAPI;

    public void setRedisAPI(RedisAPI redisAPI) {
        this.redisAPI = redisAPI;
    }


    @Override
    public String generateToken(String userAgent, ItripUser user) throws Exception {
        StringBuilder str = new StringBuilder();
        str.append("token:");
        //判断设备
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        if (agent.getOperatingSystem().isMobileDevice()) {
            str.append("MOBILE-");
        } else {
            str.append("PC-");
        }
        str.append(MD5.getMd5(user.getUserCode(), 32) + "-");
        str.append(user.getId() .toString() + "-");
        str.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-");
        str.append(MD5.getMd5(userAgent, 6));
        return str.toString();
    }

    @Override
    public void save(String token, ItripUser user) throws Exception {
        if (token.startsWith("token:PC-")) {
            //pc端时间限制
            redisAPI.set(token, 2 * 60 * 60, JSON.toJSONString(user));
        } else {
            redisAPI.set(token, JSON.toJSONString(user));
        }
    }

    @Override
    public boolean validate(String userAgent, String token) throws Exception {
        if (!redisAPI.exists(token)) {
            //不存在token
            return false;
        }
        String agentMD5 = token.split("-")[4];
        if (!MD5.getMd5(userAgent, 6).equals(agentMD5)) {
            //agent不一致
            return false;
        }
        return true;
    }

    @Override
    public Long delete(String token) {
        return redisAPI.del(token);
    }

    /*保护期 时间*/
    private long protectedTime = 30 * 60;
    private int delay = 2 * 60;

    @Override
    public String reloadToken(String userAgent, String token) throws Exception {
//1.验证token是否有效
        if (!redisAPI.exists(token)) {
            throw new Exception("token无效");
        }
        //2.能不能置换
        Date gentime =
                new SimpleDateFormat("yyyyMMddHHmmss").parse(token.split("-")[3]);
        long passed = Calendar.getInstance().getTimeInMillis() - gentime.getTime();
        if (passed < protectedTime * 1000) {
            throw new Exception("token置换保护期内,不能置换,剩余" + (protectedTime * 1000 - passed) / 1000);
        }
        //3进行置换
        //  依据key 解析ItripUser
        String user = redisAPI.get(token);
        ItripUser itripUser = JSON.parseObject(user, ItripUser.class);
        String newToken = this.generateToken(userAgent, itripUser);
        //4.旧的token延时过期
        redisAPI.set(token, delay, user);
        //5.新的token保存至redis
        this.save(newToken, itripUser);
        return newToken;
    }
}
