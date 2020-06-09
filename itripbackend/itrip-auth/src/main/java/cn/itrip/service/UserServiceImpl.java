package cn.itrip.service;

import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.order.ItripPersonalHotelOrderVO;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import cn.itrip.dao.user.ItripUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private ItripUserMapper itripUserMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisAPI redisAPI;

    @Autowired
    private SmsService smsService;

    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }

    public void setRedisAPI(RedisAPI redisAPI) {
        this.redisAPI = redisAPI;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public void setItripUserMapper(ItripUserMapper itripUserMapper) {
        this.itripUserMapper = itripUserMapper;
    }

    @Override
    public ItripUser login(String userCode, String userPassword) throws Exception {
    ItripUser user=this.findUserByUserCode(userCode);
    if(user!=null&&user.getUserPassword().equals(userPassword)){
        if(user.getActivated()!=-1)
            throw new Exception("用户未激活");
            return user;
        }
        return null;
    }

    @Override
    public ItripUser findUserByUserCode(String userCode) {
        ItripUser user=null;
        try {
           user= itripUserMapper.findUserByUserCode(userCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  user;
    }

    @Override
    public void itriptxCreateUser(ItripUser user) throws Exception {
        //1添加用户信息
        itripUserMapper.insertItripUser(user);
        //2生成激活码
       String activationCode= MD5.getMd5(new Date().toLocaleString(),32);
        //3发送邮件
        mailService.sendActivationMail(user.getUserCode(),activationCode);
        //4.、激活码存入redis
        redisAPI.set("activation:"+user.getUserCode(),30*60,activationCode);
    }

    @Override
    public boolean activate(String mail, String code) throws Exception {
        //1验证激活码
        String value=redisAPI.get("activation:"+mail);
        if(value!=null&&value.equals(code)){
        //2更新用户
        ItripUser user=itripUserMapper.findUserByUserCode(mail);
        user.setActivated(-1);
        user.setUserType(1);//用户自注册
        user.setFlatID(user.getId());
        itripUserMapper.updateItripUser(user);
        return true;
}
        return false;
    }

    @Override
    public void itriptxCreateUserByPhone(ItripUser user) throws Exception {
        //1.创建用户
        itripUserMapper.insertItripUser(user);
        //2.生成验证码
        int code=MD5.getRandomCode();
        //3.发送验证码 1分钟输入有效
        smsService.send(user.getUserCode(),"1",new String[]{String.valueOf(code),"1"});
        //4.缓存验证码到redis
        redisAPI.set("activation:"+user.getUserCode(),60,String.valueOf(code));
    }

    @Override
    public boolean validatePhone(String phoneNum, String code) throws Exception {
        //1.对比验证码
            String value=redisAPI.get("activation:"+phoneNum);
            if(value!=null&&value.equals(code)){
                ItripUser user =this.findUserByUserCode(phoneNum);
                if(user!=null){
                    user.setActivated(-1);
                    user.setUserType(2);
                    user.setFlatID(user.getId());
                    itripUserMapper.updateItripUser(user);
                    return true;
                }
            }
        //2.更新用户激活状态
        return false;
    }


}
