package cn.itrip.service;

import cn.itrip.beans.pojo.ItripImage;
import cn.itrip.beans.pojo.ItripUser;

public interface TokenService {
    /**
     * 生成token字符串
     * 前缀-Usercode-userid-creationdate-round6位
     * @return
     * @throws Exception
     */
    public String generateToken(String userAgent, ItripUser user)    throws  Exception;

    /**
     * 保存token至redis
     * @throws Exception
     */
    public void save(String token, ItripUser user) throws Exception;

    /**
     * 验证token是否有效
     * @param userAgent
     * @param token
     * @return
     * @throws Exception
     */
    public boolean validate(String userAgent,String token) throws  Exception;

    /**
     * 删除token
     * @param token
     * @return
     */
    public  Long delete(String token);

    /**
     * 置换token
     * @param token
     * @return
     */
    public String reloadToken(String userAgent, String token) throws Exception;
}
