package com.wang.test.service;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wangcan
 * @Date: 2020/11/10 16:16
 * 雪花测试
 */
@Slf4j
public class SnowFlake {
    /**
     * 时间部分所占长度
     */
    private static final int TIME_LEN = 41;
    /**
     * 数据中心id所占长度
     */
    private static final int DATA_LEN = 5;
    /**
     * 机器id所占长度
     */
    private static final int WORK_LEN = 5;
    /**
     * 毫秒内序列所占长度
     */
    private static final int SEQ_LEN = 12;
    /**
     * 定义起始时间 2015-01-01 00:00:00
     */
    private static final long START_TIME = 1420041600000L;
    /**
     * 上次生成id的时间戳
     */
    private static long LAST_TIME_STAMP = -1L;
    /**
     * 时间部分向左移动的位数 22
     */
    private static final int TIME_LEFT_BIT = 64 - 1 - TIME_LEN;
    /**
     * 自动获取数据中心id（可以手动定义0-31之间的任意数）
     */
    private static final long DATA_ID = getDataId();
    /**
     * 自动获取机器id（可以手动定义0-31之间的任意数）
     */
    private static final long WORK_ID = getWorkId();
    /**
     * 数据中心id最大值 31
     */
    private static final int DATA_MAX_NUM = ~(-1 << DATA_LEN);
    /**
     * 机器id最大值 31
     */
    private static final int WORK_MAX_NUM = ~(-1 << WORK_LEN);
    /**
     * 随机获取数据中心id的参数 32
     */
    private static final int DATA_RANDOM = DATA_MAX_NUM + 1;
    /**
     * 随机获取机器id的参数 32
     */
    private static final int WORK_RANDOM = WORK_MAX_NUM + 1;
    /**
     * 数据中心id左位移数 17
     */
    private static final int DATA_LEFT_BIT = TIME_LEFT_BIT - DATA_LEN;
    /**
     * 机器id左位移数 12
     */
    private static final int WORK_LEFT_BIT = DATA_LEFT_BIT - WORK_LEN;
    /**
     * 上一次毫秒内序列值
     */
    private static long LAST_SEQ = 0L;
    /**
     * 毫秒内序列的最大值 4095
     */
    private static final long SEQ_MAX_NUM = ~(-1 << SEQ_LEN);
    private final Object object = new Object();

    public synchronized static long getId() {
        long now = System.currentTimeMillis();
        //如果当前时间小于上次ID生成的时间，说明系统回退过抛出异常
        if (now < LAST_TIME_STAMP) {
            log.info("系统时间异常，请检查！");
            throw new RuntimeException("系统时间异常！");
        }

        if (now == LAST_TIME_STAMP) {
            LAST_SEQ = (LAST_SEQ + 1) & SEQ_MAX_NUM;
            if (LAST_SEQ == 0) {
                now = nextMillis(LAST_TIME_STAMP);
            }
        } else {
            LAST_SEQ = 0;
        }

        LAST_TIME_STAMP = now;

        return ((now - START_TIME) << TIME_LEFT_BIT) | (DATA_ID << DATA_LEFT_BIT) | (WORK_ID << WORK_LEFT_BIT) | LAST_SEQ;
    }

    private static long nextMillis(Long lastMillis) {
        long now = System.currentTimeMillis();
        while (now <= lastMillis) {
            now = System.currentTimeMillis();
        }
        return now;
    }

    private static int getDataId() {
        try {
            return getHostId(Inet4Address.getLocalHost().getHostName(), DATA_MAX_NUM);
        } catch (UnknownHostException e) {
            return new Random().nextInt(DATA_RANDOM);
        }
    }

    private static int getWorkId() {
        try {
            return getHostId(Inet4Address.getLocalHost().getHostAddress(), WORK_MAX_NUM);
        } catch (UnknownHostException e) {
            return new Random().nextInt(WORK_RANDOM);
        }
    }

    private static int getHostId(String str, int max) {
        byte[] bytes = str.getBytes();
        int sums = 0;
        for (int b : bytes) {
            sums += b;
        }
        return sums % (max + 1);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(getId());
                }
            }).start();

        }
    }
}
