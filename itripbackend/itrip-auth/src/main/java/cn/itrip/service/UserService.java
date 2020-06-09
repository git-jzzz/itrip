package cn.itrip.service;

import cn.itrip.beans.pojo.ItripUser;

public interface UserService {
    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @return
     * @throws Exception
     */
    public ItripUser login(String userCode,String userPassword) throws  Exception;

    /**
     * 依据用户编码返回用户信息
     * @param userCode
     * @return
     */
    public ItripUser findUserByUserCode(String userCode);

    /**
     * 邮箱注册用户
     * @param user
     * @throws Exception
     */
    public void itriptxCreateUser(ItripUser user) throws Exception;

    /**
     * 验证邮箱激活码
     * @param mail
     * @param code
     * @return
     * @throws Exception
     */
    public boolean activate(String mail,String code)throws  Exception;

    /**
     * 手机注册用户
     * @param user
     * @throws Exception
     */
    public void itriptxCreateUserByPhone(ItripUser user) throws  Exception;

    /**
     * 手机验证码验证
     * @param phoneNum
     * @param code
     * @return
     * @throws Exception
     */
    public boolean validatePhone(String phoneNum,String code) throws Exception;
}
