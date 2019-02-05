package com.hyd.cache.caches.cache2k;

import com.hyd.cache.CacheAdapterFactory;
import com.hyd.cache.CacheConfiguration;

public class Cache2kConfiguration extends CacheConfiguration {

    static {
        CacheAdapterFactory.register(
                Cache2kConfiguration.class,
                config -> new Cache2kAdapter((Cache2kConfiguration) config)
        );
    }

    private long entryCapacity = 10000;

    private long expireAfterWriteMillis = 3600000;

    public long getEntryCapacity() {
        return entryCapacity;
    }

    public void setEntryCapacity(long entryCapacity) {
        this.entryCapacity = entryCapacity;
    }

    public long getExpireAfterWriteMillis() {
        return expireAfterWriteMillis;
    }

    public void setExpireAfterWriteMillis(long expireAfterWriteMillis) {
        this.expireAfterWriteMillis = expireAfterWriteMillis;
    }
}
