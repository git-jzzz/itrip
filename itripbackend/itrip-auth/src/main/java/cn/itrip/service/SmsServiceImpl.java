package cn.itrip.service;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("smsService")
public class SmsServiceImpl implements  SmsService {


    @Override
    public void send(String to, String templateId, String[] datas) throws Exception {
        CCPRestSmsSDK sdk=new CCPRestSmsSDK();
        sdk.init("app.cloopen.com","8883");//初始化生产url
        sdk.setAccount("8a216da86e011fa3016eaff49d81688f","707a95147a2e428c8026fef89ea9fb43");
        sdk.setAppId("8a216da86e011fa3016eaff49ddf6895");

        HashMap result =sdk.sendTemplateSMS(to,templateId,datas);

        if(result.get("statusCode").equals("000000")){
            System.out.println("短信发送成功!");
        }else{
            throw new Exception(result.get("statusCode").toString()+result.get("statusMsg").toString());
        }
    }
}
