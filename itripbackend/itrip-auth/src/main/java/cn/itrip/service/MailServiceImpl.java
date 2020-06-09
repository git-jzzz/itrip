package cn.itrip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * 邮件发送
 */
@Service("mailService")
public class MailServiceImpl  implements MailService{

    @Autowired
    private SimpleMailMessage mailMessage;

    @Autowired
    private MailSender mailSender;

    public void setMailMessage(SimpleMailMessage mailMessage) {
        this.mailMessage = mailMessage;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendActivationMail(String mailTo, String activationCode) {
        mailMessage.setTo(mailTo);
        mailMessage.setText("您的激活码是:"+activationCode);
        mailSender.send(mailMessage);
    }
}
