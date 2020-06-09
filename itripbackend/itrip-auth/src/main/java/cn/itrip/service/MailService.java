package cn.itrip.service;

public interface MailService {
    public void sendActivationMail(String mailTo,String activationCode);
}
