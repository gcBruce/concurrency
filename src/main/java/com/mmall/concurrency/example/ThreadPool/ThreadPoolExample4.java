package com.mmall.concurrency.example.ThreadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolExample4 {


    public static void main(String[] args) {
        ScheduledExecutorService executorService=Executors.newScheduledThreadPool(5);
//        for(int i=0;i<10;i++){
//            final int index=i;
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    log.info("task:{}",index);
//                }
//            });
//        }

        executorService.schedule(()->{
            log.warn("schedule run");
        },3,TimeUnit.SECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.warn("schedule run1");
            }
        },1,3,TimeUnit.SECONDS);
        //executorService.shutdown();

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.warn("timer run2");
            }
        },new Date(),5*1000);
    }
}
