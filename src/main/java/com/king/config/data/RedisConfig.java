package com.king.config.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxIdle(50);
        config.setMaxTotal(100);
        config.setMaxWaitMillis(20000);
        return config;
    }

    @Bean
    @Deprecated
    public RedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connectionFactory=new JedisConnectionFactory();
        connectionFactory.setHostName("localhost");
        connectionFactory.setPoolConfig(jedisPoolConfig());
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
