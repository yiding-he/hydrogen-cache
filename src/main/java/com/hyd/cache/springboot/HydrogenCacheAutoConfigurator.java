package com.hyd.cache.springboot;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CacheAutoConfig.class)
public class HydrogenCacheAutoConfigurator {

    @Bean
    Caches caches(CacheAutoConfig configuration) {
        return new Caches(configuration);
    }
}
