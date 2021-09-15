package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.models.Count;
import com.models.TrafficInfo;
import com.service.TrafficInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.util.TrafficMethod.*;
import static com.zzrb.ecc.AnboECCKey.generateKeyPair;

/**
 * 实现创建多个流量主对应的多个车场，广告位默认创建5个
 */
@RestController
@RequestMapping("/")
@Slf4j
public class CreateTraffic {
    String name = "";

    String parkName = "";
    String parkId = "";
    String message="";
    String status="";
    @Resource
    TrafficInfoService trafficInfoService;


    @PostMapping("create")
    public String createTraffic(Count trafficCount) throws Exception {
        List list = new ArrayList();
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        if(trafficCount.getCount()==null || trafficCount.getCount()==0){
            return "必须输入大于0的整数";
        }
        for (int i = trafficCount.getCount(); i > 0; i--) {
            /*============生成秘钥并插入数据库中==============*/
            //获取公私钥
            String getKey = generateKeyPair();
            //提取公私钥
            JSONObject jsonObject = JSON.parseObject(getKey);
            String PrivKey = jsonObject.getString("priv_key");
            String PubKey = jsonObject.getString("pub_key");
            JSONObject jsonObject1 = JSON.parseObject(PrivKey);
            String priv = jsonObject1.getString("value");
            JSONObject jsonObject2 = JSON.parseObject(PubKey);
            String pub = jsonObject2.getString("value");
            //将数据插入数据库中
            TrafficInfo trafficInfo = new TrafficInfo();
            trafficInfo.setPublicKey(pub);
            trafficInfo.setPrivateKey(priv);
            trafficInfoService.insert(trafficInfo);
            log.debug("插入数据库秘钥信息成功");

            /*============创建流量主==============*/
//            根据返回的json获取流量主名称
            String result = createCount(pub, priv);
            JSONObject jsonObject3 = JSON.parseObject(result);
            name = jsonObject3.getString("name");
            String partnerId = jsonObject3.getString("partnerId");
            System.out.println("====流量主===" + name);
            System.out.println("====partnerId===" + partnerId);
            list.add(name);


            /*============创建车场==============*/
            for (int j = trafficCount.getParkCount(); j > 0; j--) {
                //从数据库中根据流量主partnerId获取私钥private_key
                trafficInfo.setPartnerId(partnerId);
                String private_key = trafficInfoService.selectBypartnerId(trafficInfo);
                System.out.println("===private_key====" + private_key);
               String  result_park = createParkById(partnerId, private_key);
               JSONObject jsonObject4=JSON.parseObject(result_park);
                parkName=jsonObject4.getString("parkName");
                parkId=jsonObject4.getString("parkId");
                System.out.println("创建车场为：" + parkId);
                list1.add(parkName);

                /*============创建广告位==============*/

                String result_adv= createAdv(parkId,partnerId,private_key);
                JSONObject jsonObject5=JSON.parseObject(result_adv);
                status=jsonObject5.getString("status");
                System.out.println(status+"=========status======");
                message=jsonObject5.getString("message");
                list2.add(message);
            }





        }

        if (name != null && name != "" && parkName!=null && parkName!="" && !list2.toString().contains("失败")) {

            return "流量主名称为：" + list.toString() + "\n" + "车场名称为：" + list1.toString()+"\n" +"创建广告位结果：["+list2.toString()+"]";
        }if(parkName == null || parkName == ""){
            return "流量主名称为：" + list.toString() + "\n" + "车场创建失败，联系管理员";
        }if(list2.toString().contains("失败")){
            return "流量主名称为：" + list.toString() + "\n" + "车场名称为：" + list1.toString()+"\n" +"创建广告位失败，联系管理员";
        }
        return "error";

    }

}

