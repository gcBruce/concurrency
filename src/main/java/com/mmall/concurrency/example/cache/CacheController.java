package com.mmall.concurrency.example.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cache")
public class CacheController {

    @Autowired
    private RedisClient redisClient;

    @RequestMapping("set")
    public String set(@RequestParam("k")String k,@RequestParam("v")String v) throws Exception {
        redisClient.set(k,v);
        return "sussess";
    }


    @RequestMapping("get")
    public String get(String k) throws Exception {
        return redisClient.get(k);
    }
}
