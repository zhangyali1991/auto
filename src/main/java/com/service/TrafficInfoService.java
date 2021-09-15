package com.service;

import com.dao.TrafficInfoDao;
import com.models.TrafficInfo;
import com.param.ExportExcelDate;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import static com.common.BaseData.IS_NO;
import static com.common.BaseData.IS_OK;


public interface TrafficInfoService{

    public void insert(TrafficInfo trafficInfo);
    public  String   selectBypartnerId(TrafficInfo trafficInfo);
    String   selectBypartnerName(TrafficInfo trafficInfo);
    TrafficInfo selectAll(String trafficName);
    List<ExportExcelDate> selectTrafficName();




}
