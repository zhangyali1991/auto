package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.models.ParkingInfo;
import com.models.TrafficInfo;
import com.service.ParkingInfoService;
import com.service.TrafficInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static com.util.TrafficMethod.createAdv;

@Controller
@RequestMapping("/")
public class CreateAdvertise {
    @RequestMapping("adv")
    public String adv() {

        return "adv.html";
    }

    @Resource
    TrafficInfoService trafficInfoService;
    @Resource
    ParkingInfoService parkingInfoService;

    @PostMapping("createAdv")
    @ResponseBody
    public String createAdvertise(String trafficName, String parkingName) throws Exception {
        if(trafficName=="" || parkingName=="" || trafficName==null || parkingName==null){
            return "参数不能为空";
        }
//        根据流量主名称获取private_key、partnerId、流量主id
        TrafficInfo trafficInfo = trafficInfoService.selectAll(trafficName);
        if(trafficInfo==null){
            return "无此流量主";
        }
        long trafficId = trafficInfo.getId();
        System.out.println(trafficId);
        String private_key = trafficInfo.getPrivateKey();
        System.out.println(private_key);
        String partnerId = trafficInfo.getPartnerId();
        System.out.println(partnerId);
//        根据流量主id、车场名称获取parkId
        ParkingInfo parkingInfo=new ParkingInfo();
        parkingInfo.setTrafficId(trafficId);
        parkingInfo.setParkingName(parkingName);
        String serialNumber= parkingInfoService.getId(parkingInfo);
        if(serialNumber==null){
            return "无此车场";
        }

        String[] arr=serialNumber.split("-",-1);
        String parkId=arr[1];
        System.out.println(arr[1]);
        String result=createAdv(parkId, partnerId, private_key);
        JSONObject jsonObject= JSON.parseObject(result);
        String message=jsonObject.getString("message");
        return message;
    }
}
