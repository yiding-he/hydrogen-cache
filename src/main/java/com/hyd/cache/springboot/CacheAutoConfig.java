package com.hyd.cache.springboot;

import com.hyd.cache.caches.cache2k.Cache2kConfiguration;
import com.hyd.cache.caches.caffeine.CaffeineConfiguration;
import com.hyd.cache.caches.ehcache.EhCacheConfiguration;
import com.hyd.cache.caches.memcached.MemcachedConfiguration;
import com.hyd.cache.caches.redis.RedisConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "hydrogen-cache")
@Data
public class CacheAutoConfig {

    private Map<String, RedisConfiguration> redis = new HashMap<>();

    private Map<String, MemcachedConfiguration> memcached = new HashMap<>();

    private Map<String, CaffeineConfiguration> caffeine = new HashMap<>();

    private Map<String, Cache2kConfiguration> cache2k = new HashMap<>();

    private Map<String, EhCacheConfiguration> ehcache = new HashMap<>();
}
