package com.mmall.concurrency.example.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class GuavaCacheExample2 {

    public static void main(String[] args) {

        //与LoadingCache的不同就是去数据库取数的方法不再写在初始化里面，而是写在get()方法里面
        Cache<String,Integer> cache=CacheBuilder.newBuilder()
                .maximumSize(10)//最多存放的数据个数
                .expireAfterWrite(10,TimeUnit.SECONDS)//缓存的时间设置
                .recordStats()//开启记录状态数据功能
                .build();

        log.info("{}",cache.getIfPresent("key1"));//只返回缓存，如果没有命中返回null   //null
        cache.put("key1",1);
        log.info("{}",cache.getIfPresent("key1"));//1
        cache.invalidate("key1");//让key1失效
        log.info("{}",cache.getIfPresent("key1"));//null


        try {
            log.info("{}",cache.get("key2", new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return -1;
                }
            }));//访问缓存，如果没有命中则返回load方法的返回值//-1
            cache.put("key2",2);
            log.info("{}",cache.get("key2", new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return -1;
                }
            }));//2

            log.info("{}",cache.size());//1

            for(int i=3;i<13;i++){//触发缓存回收策略
                cache.put("key"+i,i);
            }
            log.info("{}",cache.size());//10

            log.info("{}",cache.getIfPresent("key2"));//null

            Thread.sleep(11000);
            log.info("{}",cache.size());//10  //是指容量的大小
            log.info("{}",cache.get("key5",new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return -1;
                }
            }));//-1

            log.info("{},{}",cache.stats().hitCount(),cache.stats().missCount());//命中次数和未命中次数查询
            log.info("{},{}",cache.stats().hitRate(),cache.stats().missRate());//命中率和未命中率查询
        }catch (Exception e){
            log.error("cache exception",e);
        }
    }
}
