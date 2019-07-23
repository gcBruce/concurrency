package com.mmall.concurrency.example.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaCacheExample1 {

    public static void main(String[] args) {

        LoadingCache<String,Integer> cache=CacheBuilder.newBuilder()
                .maximumSize(10)//最多存放的数据个数
                .expireAfterWrite(10,TimeUnit.SECONDS)//缓存的时间设置
                .recordStats()//开启记录状态数据功能
                .build(new CacheLoader<String, Integer>() {//如果没有命中调用这个方法
                    @Override
                    public Integer load(String s) throws Exception {
                        return -1;//实际中根据参数从数据库中取值
                    }
                });

        log.info("{}",cache.getIfPresent("key1"));//只返回缓存，如果没有命中返回null   //null
        cache.put("key1",1);
        log.info("{}",cache.getIfPresent("key1"));//1
        cache.invalidate("key1");//让key1失效
        log.info("{}",cache.getIfPresent("key1"));//null


        try {
            log.info("{}",cache.get("key2"));//访问缓存，如果没有命中则返回load方法的返回值//-1
            cache.put("key2",2);
            log.info("{}",cache.get("key2"));//2

            log.info("{}",cache.size());//1

            for(int i=3;i<13;i++){//触发缓存回收策略
                cache.put("key"+i,i);
            }
            log.info("{}",cache.size());//10

            log.info("{}",cache.getIfPresent("key2"));//null

            Thread.sleep(11000);
            log.info("{}",cache.size());//10  //是指容量的大小
            log.info("{}",cache.get("key5"));//-1

            log.info("{},{}",cache.stats().hitCount(),cache.stats().missCount());//命中次数和未命中次数查询
            log.info("{},{}",cache.stats().hitRate(),cache.stats().missRate());//命中率和未命中率查询
        }catch (Exception e){
            log.error("cache exception",e);
        }
    }
}
