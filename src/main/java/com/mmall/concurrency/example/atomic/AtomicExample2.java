package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@ThreadSafe
public class AtomicExample2 {

    //请求总数
    public static int clientTotal=5000;

    //同时并发执行的线程数
    public static int threadTotal=200;

    //
    public static AtomicLong count=new AtomicLong(0);


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
                    add();
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
        log.info("count:{}",count.get());
    }

    public static void add(){
        count.incrementAndGet();
        //count.getAndIncrement();

    }
}
