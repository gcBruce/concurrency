package com.mmall.concurrency.example.commonUnsafe;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j

public class DateFormatExample3 {

    private static DateTimeFormatter dateTimeFormatter= DateTimeFormat.forPattern("yyyyMMdd");

    //请求总数
    public static int clientTotal=5000;

    //同时并发执行的线程数
    public static int threadTotal=200;



    public static void main(String[] args) throws InterruptedException {
        //线程池
        ExecutorService executorService= Executors.newCachedThreadPool();
        //定义信号量
        final Semaphore semaphore=new Semaphore(threadTotal);
        //定义计数器闭锁
        final CountDownLatch countDownLatch=new CountDownLatch(clientTotal);
        //放入请求
        for (int i=0;i<clientTotal;i++){
            executorService.execute(()->{
                try{
                    semaphore.acquire();
                    update();
                    semaphore.release();
                }catch (Exception e){
                    log.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        //关闭线程池
        executorService.shutdown();
    }

    public static void update(){
        DateTime.parse("20190516",dateTimeFormatter).toDate();
    }
}
