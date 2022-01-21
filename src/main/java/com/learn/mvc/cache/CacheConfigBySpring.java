package com.learn.mvc.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.*;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfigBySpring {

    /**
     * 配置缓存管理器
     *
     * @return 缓存管理器
     */
    @Bean("caffeineCacheManager")
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                                         // 设置最后一次写入或访问后经过固定时间过期
                                         .expireAfterAccess(60, TimeUnit.SECONDS)
                                         // 初始的缓存空间大小
                                         .initialCapacity(100)
                                         // 缓存的最大条数
                                         .maximumSize(1000));
        return cacheManager;
    }
    /**
     * initialCapacity	integer	初始的缓存空间大小
     * maximumSize	long	缓存的最大条数
     * maximumWeight	long	缓存的最大权重
     * expireAfterAccess	duration	最后一次写入或访问后经过固定时间过期
     * refreshAfterWrite	duration	最后一次写入后经过固定时间过期
     * refreshAfterWrite	duration	创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
     * weakKeys	boolean	打开 key 的弱引用
     * weakValues	boolean	打开 value 的弱引用
     * softValues	boolean	打开 value 的软引用
     * recordStats	-	开发统计功能
     *
     *
     * weakValues 和 softValues 不可以同时使用。
     * maximumSize 和 maximumWeight 不可以同时使用。
     * expireAfterWrite 和 expireAfterAccess 同事存在时，以 expireAfterWrite 为准。
     *
     * 软引用： 如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，就会回收这些对象的内存。
     * 弱引用： 弱引用的对象拥有更短暂的生命周期。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，不管当前内存空间足够与否，都会回收它的内存
     * // 软引用
     * Caffeine.newBuilder().softValues().build();
     * // 弱引用
     * Caffeine.newBuilder().weakKeys().weakValues().build();
     */



    // @Bean(name = "redisCacheManager")
    @Primary
    public CacheManager cacheManager(ObjectMapper objectMapper, RedisConnectionFactory redisConnectionFactory) {
        //设置序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                                                                            .disableCachingNullValues()
                                                                            //                .computePrefixWith(cacheName -> "yourAppName".concat(":").concat(cacheName).concat(":"))
                                                                            .serializeKeysWith(
                                                                                    RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                                                                            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

        return RedisCacheManager.builder(redisConnectionFactory)
                                .cacheDefaults(cacheConfiguration)
                                .build();

    }
}
