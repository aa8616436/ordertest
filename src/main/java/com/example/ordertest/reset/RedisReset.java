package com.example.ordertest.reset;

import redis.clients.jedis.Jedis;

public class RedisReset  {
    public static void main(String[] args) {
        Jedis jedis=new Jedis("localhost");
        System.out.println(jedis.flushAll());
    }
}
