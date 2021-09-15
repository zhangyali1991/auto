package com.dao;

import com.models.TrafficInfo;
import com.param.ExportExcelDate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrafficInfoDao {
 void  insert(TrafficInfo trafficInfo);//插入公私钥到数据库
 String   selectBypartnerId(TrafficInfo trafficInfo);//根据流量主id获取private_key
 String   selectBypartnerName(TrafficInfo trafficInfo);//根据流量主名称获取partner_id
 TrafficInfo selectAll(String trafficName);
 List<TrafficInfo> selectTrafficName();


}
