package com.service.impl;

import com.dao.ParkingInfoDao;
import com.models.ParkingInfo;
import com.service.ParkingInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ParkingInfoImpl implements ParkingInfoService {
    @Resource
    ParkingInfoDao parkingInfoDao;
    @Override
    public String getId(ParkingInfo parkingInfo) {
        return parkingInfoDao.getId(parkingInfo);
    }
}
