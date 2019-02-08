package com.oydipi.ratingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
    @Bean
    public Jedis jedis(JedisPool jedisPool) {
        return jedisPool.getResource();
    }

    @Bean
    public JedisPool jedisPool(@Value("${spring.redis.host}") String redisHost, @Value("${spring.redis.port}") Integer redisPost) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestWhileIdle(true);
        return new JedisPool(poolConfig, redisHost, redisPost);
    }
}
