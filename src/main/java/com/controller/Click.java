package com.controller;

import com.models.Count;
import com.models.ParkingInfo;
import com.models.TrafficInfo;
import com.service.ParkingInfoService;
import com.service.TrafficInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static com.util.TrafficMethod.click;

/**
 * 根据addid,流量主名称，车场名称进行多次点击
 */
@Controller
@RequestMapping("/")
public class Click {
    @Resource
    ParkingInfoService parkingInfoService;
    @Resource
    TrafficInfoService trafficInfoService;

    @RequestMapping(value = "redirect")
    public String redirect() {
        return "redirect.html";
    }

    @PostMapping("click")
    @ResponseBody
    public String clickNoLanding(Count count, String trafficName, String uuid, String parkingName) throws Exception {
        if(trafficName=="" || uuid=="" || parkingName=="" ){
            return "参数不能为空";
        }
        if(count.getCount()<=0){
            return "点击次数不能小于等于0";
        }
        //根据流量主名称获取私钥、流量主id；
        TrafficInfo trafficInfo = trafficInfoService.selectAll(trafficName);
        if(trafficInfo==null){
            return "无此流量主";
        }
        long id = trafficInfo.getId();
        String private_key = trafficInfo.getPrivateKey();
        ParkingInfo parkingInfo = new ParkingInfo();
        parkingInfo.setParkingName(parkingName);
        parkingInfo.setTrafficId(id);
        //根据车场名称、流量主id查询到流量主parterId、parkId
        String serialNumber = parkingInfoService.getId(parkingInfo);
        if(serialNumber==null){
            return "无此车场";
        }
        String[] arr = serialNumber.split("-", -1);
        String parkId = arr[1];
        String partnerId = arr[0];
        //点击
        for(int i=count.getCount();i>0;i--){
            click(uuid, parkId, partnerId, private_key);

        }
        return "点击成功";
    }
}
