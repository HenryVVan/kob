package com.kob.botrunningsystem.service.impl.utils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author：Henry Wan
 * @Package：com.kob.botrunningsystem.service.impl
 * @Project：backendcloud
 * @Date：2024/3/1 15:32
 * @Filename：BotPool
 */
public class BotPool extends Thread {
    private final static ReentrantLock lock = new ReentrantLock();
    // 条件变量
    private Condition condition = lock.newCondition();
    private Queue<Bot> bots = new LinkedList<>();

    public void addBot(Integer userId, String botCode, String input) {
        lock.lock(); //修改bot，先加锁
        try {
            bots.add(new Bot(userId, botCode, input));
            condition.signalAll(); // 生产者生产完，唤醒消费者
        } finally {
            lock.unlock();
        }
    }


    private void consume(Bot bot) {
        System.out.println("----------");
        // 线程 超时可以断，跟容易控制代码的执行时间
        Consumer consumer = new Consumer();
        // 超过两秒，就断
        consumer.startTimeout(2000, bot);
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (bots.isEmpty()) {
                try {
                    condition.await(); // await被唤醒 or 中断 会默认解锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock(); // 如果出现异常，需要手动解锁
                    break;
                }
            } else {
                Bot bot = bots.poll();
                lock.unlock(); // 取完bot就没必要再持有锁
                consume(bot); // 比较耗时，编译代码 需要执行几秒钟
            }
        }
    }
}

