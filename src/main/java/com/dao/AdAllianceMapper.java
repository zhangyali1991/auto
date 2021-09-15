package com.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdAllianceMapper {

    String AdIdByUuid(String uuid);

}