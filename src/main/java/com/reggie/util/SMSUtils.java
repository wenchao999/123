package com.reggie.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.reggie.util.MsmConstantUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

public class SMSUtils {
    public static boolean send(String phone, String code) {
        //判断手机是否为null
        if (StringUtils.isEmpty(phone)){
            return false;
        }
        try {
            /* 必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
             * 本示例采用从环境变量读取的方式，需要预先在环境变量中设置这两个值
             * 您也可以直接在代码中写入密钥对，但需谨防泄露，不要将代码复制、上传或者分享给他人
             * CAM 密钥查询：https://console.cloud.tencent.com/cam/capi
             */
            Credential cred = new Credential(MsmConstantUtils.SECRET_ID, MsmConstantUtils.SECRET_KEY);


            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {"+86"+phone};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setSmsSdkAppid(MsmConstantUtils.APP_ID);
            req.setSign(MsmConstantUtils.SIGN_NAME);
            req.setTemplateID(MsmConstantUtils.TEMPLATE_ID);

            String[] templateParamSet1 = {code};
            req.setTemplateParamSet(templateParamSet1);
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出 JSON 格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(resp));
            return true;
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return false;
    }
}

