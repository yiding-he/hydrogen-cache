package com.hyd.cache.caches.caffeine;

import com.hyd.cache.CacheAdapterFactory;
import com.hyd.cache.CacheConfiguration;

public class CaffeineConfiguration extends CacheConfiguration {

    static {
        CacheAdapterFactory.register(
                CaffeineConfiguration.class,
                config -> new CaffeineAdapter((CaffeineConfiguration) config)
        );
    }

    private long expireAfterAccessMillis = -1;

    private long expireAfterWriteMillis = 3600000;

    private long maximumSize = 10000;

    private boolean softValues = false;

    public long getExpireAfterAccessMillis() {
        return expireAfterAccessMillis;
    }

    public void setExpireAfterAccessMillis(long expireAfterAccessMillis) {
        this.expireAfterAccessMillis = expireAfterAccessMillis;
    }

    public long getExpireAfterWriteMillis() {
        return expireAfterWriteMillis;
    }

    public void setExpireAfterWriteMillis(long expireAfterWriteMillis) {
        this.expireAfterWriteMillis = expireAfterWriteMillis;
    }

    public long getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(long maximumSize) {
        this.maximumSize = maximumSize;
    }

    public boolean isSoftValues() {
        return softValues;
    }

    public void setSoftValues(boolean softValues) {
        this.softValues = softValues;
    }
}
