package com.service.impl;

import com.dao.AdAllianceMapper;
import com.service.AdAllianceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdAllianceImpl implements AdAllianceService {
    @Resource
    AdAllianceMapper adAllianceMapper;
    @Override
    public String AdIdByUuid(String uuid) {
        return adAllianceMapper.AdIdByUuid(uuid);
    }
}
