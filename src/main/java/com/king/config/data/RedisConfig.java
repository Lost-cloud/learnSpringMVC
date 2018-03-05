package com.king.config.data;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Collections;

@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(50);
        config.setMaxTotal(100);
        config.setMaxWaitMillis(20000);
        return config;
    }

    @Bean
    @Deprecated
    public RedisConnectionFactory connectionFactory(JedisPoolConfig poolConfig) {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName("localhost");
        connectionFactory.setPort(6379);
        connectionFactory.setPoolConfig(poolConfig);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    @Bean
    public RedisSerializer<String> stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    @SuppressWarnings("unchecked")
    public RedisTemplate redisTemplate(RedisConnectionFactory factory,
                                       RedisSerializer rs) {
        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setKeySerializer(rs);
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisCacheWriter cacheWriter(RedisConnectionFactory factory) {
        //返回不锁定的RedisCacheWriter
        // 可能导致重叠数据，非原子命令
        //需要一个连接工厂
        return RedisCacheWriter.nonLockingRedisCacheWriter(factory);
    }

    @Bean
    public RedisCacheConfiguration configuration() {
        return RedisCacheConfiguration.defaultCacheConfig()
//                .disableCachingNullValues()          //不缓存null值
                .entryTtl(Duration.ofSeconds(600));//设置清理时间
//                .prefixKeysWith("@*@");                //keys前缀
    }

    @Bean("redisCacheManager")
    public CacheManager initRedisCacheManager(RedisCacheConfiguration configuration,RedisConnectionFactory factory,
                                              RedisCacheWriter writer) {
        //默认使用非锁定的RedisCacheWriter
        //cache
        return RedisCacheManager.builder(writer)
                .cacheDefaults(configuration)
                .initialCacheNames(Collections.singleton("defaultCacheConfig"))
                .transactionAware()
                .build();
    }
}
