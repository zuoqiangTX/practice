package com.tongbanjie.jdk.AQS;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 完全是为了看源码写一个main方法
 *
 * @author xu.qiang
 * @date 18/7/5
 * @see https://javadoop.com/post/AbstractQueuedSynchronizer
 * ps:一个写AQS很不错的博客
 */
public class AQSLearning {

    /**
     * 还是要读读源码的咯
     *
     * @see ReentrantLock.FairSync
     * @see ReentrantLock.Sync
     * @see AbstractQueuedSynchronizer   【核心类】
     * @see AbstractQueuedSynchronizer.Node
     * @see AbstractOwnableSynchronizer
     */
    private static ReentrantLock reentrantLock = new ReentrantLock(true);

    private static Integer tickets = 100;

    public static void main(String[] args) {


        ExecutorService executorService = Executors.newFixedThreadPool(8);

        for (int i = 0; i < 1; i++) {

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //不支持并发的出票
//                    test01();
                    //支持并发的出票
                    test02();
                }
            });
        }


        executorService.shutdown();

    }


    private static void test01() {

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tickets = tickets - 1;
        System.out.println(Thread.currentThread().getName() + "--->剩余票数" + tickets);
    }


    private static void test02() {
        // 比如我们同一时间，只允许一个线程创建订单
        reentrantLock.lock();
        // 通常，lock 之后紧跟着 try 语句
        try {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tickets = tickets - 1;
            System.out.println(Thread.currentThread().getName() + "--->剩余票数" + tickets);
        } finally {
            // 释放锁
            reentrantLock.unlock();

        }
    }


}
