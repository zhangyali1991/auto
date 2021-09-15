package com.dao;

import com.models.AdInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdinfoDao {
   public String  getAdName(AdInfo adInfo);
}
