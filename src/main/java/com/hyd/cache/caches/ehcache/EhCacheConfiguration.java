package com.hyd.cache.caches.ehcache;

import com.hyd.cache.CacheAdapterFactory;
import com.hyd.cache.CacheConfiguration;

public class EhCacheConfiguration extends CacheConfiguration {

    private static transient int cacheCounter = 0;

    static {
        CacheAdapterFactory.register(
                EhCacheConfiguration.class,
                conf -> new EhCacheAdapter((EhCacheConfiguration) conf)
        );
    }

    private String alias = "CACHE" + cacheCounter++;

    private int resourcePoolSize = 1000;

    private int timeToLiveSeconds = 3600;

    public int getResourcePoolSize() {
        return resourcePoolSize;
    }

    public void setResourcePoolSize(int resourcePoolSize) {
        this.resourcePoolSize = resourcePoolSize;
    }

    public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
