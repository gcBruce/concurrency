package com.mmall.concurrency.example.concurrent;

import com.mmall.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Slf4j
@ThreadSafe
public class CopyOnWriteArraySetExample {

    //请求总数
    public static int clientTotal=5000;

    //同时并发执行的线程数
    public static int threadTotal=200;

    private static Set<Integer> set =new CopyOnWriteArraySet<>();

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
        log.info("size:{}",set.size());
    }

    public static void update(int i){
        set.add(i);
    }
}
