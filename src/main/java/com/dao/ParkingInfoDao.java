package com.dao;

import com.models.ParkingInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ParkingInfoDao {
    public String getId(ParkingInfo parkingInfo) ;
}
