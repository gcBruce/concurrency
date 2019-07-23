package com.mmall.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class SemaphoreExample2 {

    private final static int threadCount=200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService=Executors.newCachedThreadPool();

        final Semaphore semaphore=new Semaphore(20);
        for(int i=0;i<threadCount;i++){
            final int threadNum=i;
            executorService.execute(()->{
                try {
                    semaphore.acquire(3);//获得3个许可
                    test(threadNum);
                    semaphore.release(3);//释放3个许可
                } catch (InterruptedException e) {
                   log.error("exception",e);
                }
            });
        }
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        log.info("{}",threadNum);
        Thread.sleep(1000);
    }
}
