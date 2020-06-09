package cn.itrip.service;

public interface SmsService {

    /**
     * 手机发送验证码
     * @param to
     * @param templateId
     * @param datas
     * @throws Exception
     */
    public void  send(String to,String templateId,String[] datas) throws Exception;

    
}
