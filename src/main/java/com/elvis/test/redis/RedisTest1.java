package com.elvis.test.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @Author : 慕君Dxl
 * @CreateTime : 2024/4/17 16:37
 */
public class RedisTest1 {

    private static Jedis get() {
        Jedis jedis = new Jedis("192.168.0.207", 36255);
        jedis.auth("Sansi@2022");
        return jedis;
    }

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                get().subscribe(new JedisPubSub() {
                    private int idx = 1;

                    @Override
                    public void onMessage(String channel, String message) {
                        System.out.println(message);
                        idx++;
                        if (idx > 15) {
                            this.unsubscribe("Elvis");
                        }
                    }
                }, "Elvis");
            }
        }).start();
    }
}