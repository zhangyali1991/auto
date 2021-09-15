package com.service.impl;

import com.dao.AdinfoDao;
import com.models.AdInfo;
import com.service.AdinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class AdinfoImpl implements AdinfoService {
    @Resource
    AdinfoDao adinfoDao;
    @Override
    public String getAdName(AdInfo adInfo) {
        return adinfoDao.getAdName(adInfo);
    }
}
