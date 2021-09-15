package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.models.*;
import com.service.AdAllianceService;
import com.service.AdinfoService;
import com.service.ParkingInfoService;
import com.service.TrafficInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.util.PublicMethod.getResult;
import static com.util.TrafficMethod.getList;

/*根据流量主名称、车场、媒体、广告页获取广告列表*/
@Controller
@RequestMapping("/")
public class Explorer {
    String adId = "";
    String pre_adId = "";

    @Resource
    TrafficInfoService trafficInfoService;
    @Resource
    ParkingInfoService parkingInfoService;
    @Resource
    AdinfoService adinfoService;
    @Resource
    AdAllianceService adAllianceService;

    @RequestMapping(value = "exp")
    public String exp() {
        return "explorer.html";
    }

    @PostMapping("open")
    @ResponseBody
    public String open(Count count, String trafficName, String parkingName, String medium, String id,String userLicence,String openId) throws Exception {
        if(trafficName==""|| trafficName==null||parkingName==""||parkingName==null||medium==""||medium==null|| id==""||id==null){
            return "参数不能为空";
        }
        if(count.getCount()<=0){
            return "曝光点击次数不能小于等于0";
        }
        List list = new ArrayList();
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        List list3 = new ArrayList();
        List list4 = new ArrayList();
        for (int i = count.getCount(); i > 0; i--) {
            //根据流量主名称获取partnerId、private_key，traffid
            TrafficInfo trafficInfo = trafficInfoService.selectAll(trafficName);
            if(trafficInfo==null){
                return "无此流量主";
            }
            String partnerId = trafficInfo.getPartnerId();
            String private_key = trafficInfo.getPrivateKey();
            Long traficId = trafficInfo.getId();
            //根据车场名称,流量主id获取park_id
            ParkingInfo parkingInfo = new ParkingInfo();
            parkingInfo.setTrafficId(traficId);
            parkingInfo.setParkingName(parkingName);
            String serialNumber = parkingInfoService.getId(parkingInfo);
            if(serialNumber==null){
                return "无此车场";
            }
            String[] arr = serialNumber.split("-", -1);
            String parkId = arr[1];
            String responseData = getList(partnerId, parkId, medium, id, private_key,userLicence,openId);

            JSONObject jsonObject = JSON.parseObject(responseData);
            String message = jsonObject.getString("message");
            String status = jsonObject.getString("status");
            String result = jsonObject.getString("result");
            if (status.equals("20000000")) {
                JSONArray jsonObject1 = JSON.parseArray(result);
                String object = "";
                for (int j = 0; j < jsonObject1.size(); j++) {
                    object = jsonObject1.get(j).toString();
                    JSONObject jsonObject2 = JSON.parseObject(object);
                    pre_adId = jsonObject2.getString("adId");
                    adId=pre_adId.split("-",0)[1];
                    if(adId.contains("adclick")){
                        adId=adAllianceService.AdIdByUuid(adId);
                    }
                    list.add(adId);
                    AdInfo adInfo = new AdInfo();
                    adInfo.setUuid(adId);

                    String adName = adinfoService.getAdName(adInfo);
                    list3.add(adName);
                    list4.add(pre_adId);
                }


            }
            list1.add(message);
            list2.add(status);

        }

        String ss = getResult(list2, "20000000");
        if (ss.equals("20000000")) {
//            return "曝光成功，计划id为：（" + list.toString() + ")" + "计划名称为：" + "{" + list3.toString() + "}";
            return "曝光成功,计划名称为："  + list3.toString()+"adId:"+list4.toString() ;
        }
        if (!ss.equals("20000000")) {
            return "曝光失败原因：" + list1.toString();
        }
        return "error";
    }

}
