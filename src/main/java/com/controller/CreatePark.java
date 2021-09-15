package com.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.models.ParkingInfo;
import com.models.TrafficInfo;
import com.service.ParkingInfoService;
import com.service.TrafficInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;
import org.assertj.core.api.Assertions;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.util.TrafficMethod.createAdv;
import static com.util.TrafficMethod.createParkById;
/*场景一：
当整个controller类全是http接口的调用时和前后端分离的项目时，这种场景下就可以使用@RestController，可以不用在每个方法上都写@ResponseBody了。
场景二：
如果controller的中方法响应需要到某个具体的html页面，那么就在类上加@Controller。
方法的返回String类型的字符串就行了。
场景三：
1、既需要http接口的调用返回，也需要跳转页面的情况，在类上加@Controller；
2、在需要http类型的接口调用返回对象是在该方法上加上@ResponseBody；3、在返回html的页面，不能加@ResponseBody，正常返回即可。*/

@Controller
@RequestMapping("/")
@Slf4j
public class CreatePark {
    @Resource
    TrafficInfoService trafficInfoService;
    @Resource
    ParkingInfoService parkingInfoService;


    @RequestMapping(value = "park")
    public String park() {
        return "park.html";
    }

    @PostMapping("createPark")
    @ResponseBody
    public String createPark(String trafficName) throws Exception {
        if(trafficName==""|| trafficName==null){
            return "参数不能为空";
        }

        /*根据流量主名称创建车场*/
        //根据流量主名称查找出partnerId
//        TrafficInfo trafficInfo = new TrafficInfo();
//        trafficInfo.setTrafficName(trafficName);
//        String partnerId = trafficInfoService.selectBypartnerName(trafficInfo);
        TrafficInfo trafficInfo = trafficInfoService.selectAll(trafficName);
        if(trafficInfo==null){
            return "无此流量主";
        }
        long id=trafficInfo.getId();
        String partnerId =trafficInfo.getPartnerId();
                //从数据库中根据流量主id获取私钥
                System.out.println("partnerId========" + partnerId);
        trafficInfo.setPartnerId(partnerId);
        String private_key = trafficInfoService.selectBypartnerId(trafficInfo);
        System.out.println(private_key);
        String result = createParkById(partnerId, private_key);
        JSONObject jsonObject = JSON.parseObject(result);
        String parkName = jsonObject.getString("parkName");
        //创建广告位

        long trafficId = trafficInfo.getId();
        ParkingInfo parkingInfo = new ParkingInfo();
        parkingInfo.setParkingName(parkName);
        parkingInfo.setTrafficId(id);
        String serialNumber = parkingInfoService.getId(parkingInfo);
        String[] arr = serialNumber.split("-", -1);
        String parkId = arr[1];
        String result_adv = createAdv(parkId, partnerId, private_key);
        JSONObject jsonObject1 = JSON.parseObject(result_adv);
        String message = jsonObject1.getString("message");
        String status = jsonObject1.getString("status");

        if (parkName!=null && parkName!="" && status.equals("20000000")) {
            return "车场名称为：(" + parkName + ")\n创建所有广告位结果:[" + message + "]";
        }if(!status.equals("20000000")){
            return "创建广告位失败"+message;
        }

        return "创建失败，请联系管理员";

    }
}
