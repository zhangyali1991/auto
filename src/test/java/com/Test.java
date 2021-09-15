package com;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoApplication.class)
public class Test {
    @Autowired
//    private RedisTemplate<String,String> redisTemplate;
    private RedisTemplate redisTemplate;
    @org.junit.Test
    public void set(){
        redisTemplate.opsForValue().set("myKey1","myValue1");
        System.out.println( "==========mykey========="+redisTemplate.opsForValue().get("myKey1"));
    }
}
