package com.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.BaseData;
import com.common.DataTest;
import com.zzrb.util.ECCCryptUtil;
import com.zzrb.util.ECCSignUtil;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.HashMap;
import java.util.Map;

import static com.common.DataTest.click_url;
import static com.util.HttpGG.*;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class TrafficMethod {

    //    流量主信息
    public static String createCount(String public_key, String private_key) throws Exception {
        Map<String, String> createAccountMap = new HashMap<String, String>();
        createAccountMap.put("name", "ZYL流量主Auto" + System.currentTimeMillis());//用户名称
        createAccountMap.put("regType", DataTest.regType);//注册类型
        createAccountMap.put("cityId", DataTest.cityId);//所在城市
        createAccountMap.put("accountType", DataTest.accountType);//客户类型? 0:车场; 1:媒介; 3:代理商
        createAccountMap.put("contactName", DataTest.contactName);//联系人
        createAccountMap.put("contactMobile", "188" + RandomStringUtils.randomNumeric(8));//联系电话
        createAccountMap.put("source", DataTest.source);//客户来源
        createAccountMap.put("manager", DataTest.manager);//客户经理
        createAccountMap.put("accountName", DataTest.accountName);//开户人名称
        createAccountMap.put("bankName", DataTest.bankName);//银行名称
        createAccountMap.put("accountNo", DataTest.accountNo);//银行名称
        createAccountMap.put("password", ECCCryptUtil.encrypt(DataTest.password, BaseData.encrypt));//登录密码
        createAccountMap.put("publicKey", public_key);//公钥
        String createAccountSign = ECCSignUtil.sign(private_key, createAccountMap);
        System.out.println("创建流量主sign ： " + createAccountSign);
        createAccountMap.put("sign", createAccountSign);
        String json = JSONObject.toJSONString(createAccountMap);
        String responseData = HttpGG.doPost(DataTest.create_account_url, json);
        System.out.println("响应参数为：" + responseData);
        JSONObject jsonObject = JSON.parseObject(responseData);
        String result = jsonObject.getString("result");
//        JSONObject jsonObject1=JSON.parseObject(result);
//        String traficName=jsonObject1.getString("name");
//        System.out.println("流量主名称为："+traficName);
        return result;
    }

    //    创建车场
    public static String createParkById(String partnerId, String private_key) throws Exception {
        //组装车场信息
        Map<String, String> parkMap = new HashMap<>();
        parkMap.put("parkName", "ZYL车场Auto" + System.currentTimeMillis());
        parkMap.put("cityId", DataTest.cityId);
        parkMap.put("partnerId", partnerId);
        String parkSign = ECCSignUtil.sign(private_key, parkMap);
        parkMap.put("sign", parkSign);
        String park_json = JSONObject.toJSONString(parkMap);
        String requestData = doPost(DataTest.park_url, park_json);
        JSONObject jsonObject = JSON.parseObject(requestData);
        String result = jsonObject.getString("result");

        return result;


    }

    //    创建广告位
    public static String createAdv(String parkId, String partnerId, String private_key) throws Exception {
        Map<String, String> adPosMap = new HashMap<>();
        adPosMap.put("adPosIds", "1#2#3#4#5");
        adPosMap.put("parkId", parkId);
        adPosMap.put("partnerId", partnerId);
        adPosMap.put("medium", "1,2,4");
        String adPosSign = ECCSignUtil.sign(private_key, adPosMap);
        adPosMap.put("sign", adPosSign);
        String adPos_json = JSONObject.toJSONString(adPosMap);
        String result = doPut(DataTest.adPos_url_v3, adPos_json);
        return result;


    }

    //    获取广告列表
    public static String getList(String partnerId, String park_Id, String medium, String id, String private_key,String userLicense,String openId ) throws Exception {
        Map<String, String> getAdvertMap = new HashMap<>();
        getAdvertMap.put("parkId", park_Id);
        getAdvertMap.put("partnerId", partnerId);
        getAdvertMap.put("adPosId", id);
        if(userLicense==null || userLicense=="" || openId==null || openId==""){
            getAdvertMap.put("userLicense", "京A" + randomNumeric(5));
            getAdvertMap.put("openId", randomNumeric(8));
        }
        getAdvertMap.put("userLicense", userLicense);
        getAdvertMap.put("openId",openId);
        getAdvertMap.put("userMobile", "188" + randomNumeric(7));


        getAdvertMap.put("userIp", randomNumeric(2) + ".1.1." + randomNumeric(2));
        getAdvertMap.put("medium", medium);
        String exposure_sign = ECCSignUtil.sign(private_key, getAdvertMap);
        getAdvertMap.put("sign", exposure_sign);
        String exposure_json = JSONObject.toJSONString(getAdvertMap);
        String responseData = doGet02(DataTest.exposure_urlNew, exposure_json);
//        JSONObject jsonObject=JSON.parseObject(responseData);
//        String result=jsonObject.getString("result");
        return responseData;
    }
//    点击
    public static void click(String uuid,String park_Id,String partnerId,String private_key) throws Exception {
       String openId=randomNumeric(8);
        String userMobile="188" + randomNumeric(7);
        String userLicense="京A" + randomNumeric(5);
        Map<String, String> clickMap = new HashMap<>();
        clickMap.put("adId", uuid);
        clickMap.put("parkId", park_Id);
        clickMap.put("partnerId", partnerId);
        clickMap.put("userMobile",userMobile );
        clickMap.put("userLicense",userLicense );
        clickMap.put("openId", openId);
        String sign = ECCSignUtil.sign(private_key, clickMap);
        clickMap.put("sign", sign);
        String click_json = JSONObject.toJSONString(clickMap);
        String click_params = "?" + "adId=" + uuid + "&" + "partnerId=" + partnerId + "&" + "parkId=" + park_Id + "&" + "openId=" + openId + "&" + "userMobile=" + userMobile +"&"+"userLicense="+userLicense+ "&" + "sign=" + sign;
        String res = "";
        try {
            res = doGet02(click_url + click_params, click_json);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
