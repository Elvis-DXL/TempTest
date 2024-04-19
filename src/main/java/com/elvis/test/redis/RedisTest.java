package com.elvis.test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.TimeUnit;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/4/17 16:37
 */
public class RedisTest {

    private static Jedis get() {
        Jedis jedis = new Jedis("192.168.0.207", 36255);
        jedis.auth("Sansi@2022");
        return jedis;
    }


    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                get().subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.println(channel + "--------" + message);
                    }
                }, "Elvis1");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                get().subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.println(channel + "--------" + message);
                    }
                }, "Elvis2");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = get();
                for (int idx = 1; idx < 10; idx++) {
                    jedis.publish("Elvis" + (idx % 2 == 0 ? "1" : "2"), "测试消息-" + idx);
                }
            }
        }).start();
    }
}