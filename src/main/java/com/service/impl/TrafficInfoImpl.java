package com.service.impl;

import com.dao.TrafficInfoDao;
import com.models.TrafficInfo;
import com.param.ExportExcelDate;
import com.service.TrafficInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrafficInfoImpl implements TrafficInfoService {
    @Resource
    TrafficInfoDao trafficInfoDao;

    @Override
    public void insert(TrafficInfo trafficInfo) {
        trafficInfoDao.insert(trafficInfo);
    }

    @Override
    public String selectBypartnerId(TrafficInfo trafficInfo) {
        return  trafficInfoDao.selectBypartnerId(trafficInfo);

    }

    @Override
    public String selectBypartnerName(TrafficInfo trafficInfo) {
        return trafficInfoDao.selectBypartnerName(trafficInfo);
    }

    @Override
    public TrafficInfo selectAll(String trafficName) {
        return trafficInfoDao.selectAll(trafficName);
    }

    @Override
    public List<ExportExcelDate> selectTrafficName() {

        List<TrafficInfo> list = trafficInfoDao.selectTrafficName();
        List<ExportExcelDate> list1=new ArrayList<>();
        for (TrafficInfo trafficInfo:list) {
            String trafficName = trafficInfo.getTrafficName();
            ExportExcelDate exportExcelDate=new ExportExcelDate();
            exportExcelDate.setTrafficName(trafficName);
            list1.add(exportExcelDate);
        }

        return list1;
    }
}
