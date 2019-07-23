package com.mmall.concurrency.example.syncContainer;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@ThreadSafe
public class CollectionsExample2 {

    //请求总数
    public static int clientTotal=5000;

    //同时并发执行的线程数
    public static int threadTotal=200;

    private static Set<Integer> set = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws InterruptedException {
        //线程池
        ExecutorService executorService= Executors.newCachedThreadPool();
        //定义信号量
        final Semaphore semaphore=new Semaphore(threadTotal);
        //定义计数器闭锁
        final CountDownLatch countDownLatch=new CountDownLatch(clientTotal);
        //放入请求
        for (int i=0;i<clientTotal;i++){
            final int count=i;
            executorService.execute(()->{
                try{
                    semaphore.acquire();
                    //传入线程池的参数需要final类型的
                    update(count);
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
        log.info("size:{}", set.size());
    }

    public static void update(int i){
        set.add(i);
    }
}
