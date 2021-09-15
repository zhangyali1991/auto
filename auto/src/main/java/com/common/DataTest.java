package com.common;

import com.zzrb.enumm.CityIdEnum;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class DataTest {
    public static final String url_open = "http://api-sit.anbokeji.net:8000";
    public static final String url_click = "http://ad-sit.anbokeji.net:8000";


    //    流量主账户信息
    public static final String name = "ZYL流量主Auto" + System.currentTimeMillis(); //用户名称            *****不能重复*****
    public static final String contactMobile = "188" + RandomStringUtils.randomNumeric(8); //联系电话  *****不能重复*****
    public static final String regType = "1"; //注册类型
    public static final String cityId = CityIdEnum.TAIYUAN.getCode(); //所在城市
    public static final String accountType = "0"; //客户类型？？？？0:车场; 1:媒介; 3:代理商  ？？？
    public static final String contactName = "风先生"; //联系人
    public static final String source = "商务谈判"; //客户来源
    public static final String manager = "张经理"; //客户经理
    public static final String accountName = "周小川"; //开户人名称
    public static final String bankName = "工商银行"; //银行名称
    public static final String accountNo = "00018877777776666"; //银行名称
    public static final String password = "88888888";
    public static final String create_account_url = url_open + "/api/v1/account";
    public static final String park_url = url_open + "/api/v2/park";
    //车场信息
    public static final String parkName = "ZYL车场Auto" + System.currentTimeMillis();// 车场名称 *****不能重复*****
    //广告位信息
    public static final String adPos_url_v3 = url_open + "/api/v3/advert-pos";
    //获取广告列表
    public static final String exposure_urlNew = url_open + "/api/v1/advert/list";
    //    广告跳转
    public static final String click_url = url_click + "/api/v1/advert/redirect/";


}
