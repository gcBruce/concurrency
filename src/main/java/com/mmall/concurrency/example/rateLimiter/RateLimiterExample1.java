package com.mmall.concurrency.example.rateLimiter;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RateLimiterExample1 {

    //令牌桶算法
    private static RateLimiter rateLimiter=RateLimiter.create(5);

    public static void main(String[] args) throws InterruptedException {

        for(int index=0;index<100;index++){
            //Thread.sleep(100);
            if(rateLimiter.tryAcquire(205,TimeUnit.MILLISECONDS)){
                handle(index);
            }
        }
    }

    private static void handle(int i){
        log.info("{}",i);
    }


}
