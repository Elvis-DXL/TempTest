package com.elvis.test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/4/17 16:37
 */
public class RedisTest2 {

    private static Jedis get() {
        Jedis jedis = new Jedis("192.168.0.207", 36255);
        jedis.auth("Sansi@2022");
        return jedis;
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = get();
                int idx = 1;
                while (true) {
                    jedis.publish("Elvis", "测试消息-" + idx);
                    idx++;
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
}