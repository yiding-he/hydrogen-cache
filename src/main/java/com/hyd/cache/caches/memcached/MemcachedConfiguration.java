package com.hyd.cache.caches.memcached;

import com.hyd.cache.CacheAdapterFactory;
import com.hyd.cache.CacheConfiguration;
import com.hyd.cache.serialization.PredefinedSerializeMethod;

import java.util.ArrayList;
import java.util.List;

public class MemcachedConfiguration extends CacheConfiguration {

    static {
        CacheAdapterFactory.register(
                MemcachedConfiguration.class,
                conf -> new MemcachedAdapter((MemcachedConfiguration) conf)
        );
    }

    private List<ServerAddress> addresses = new ArrayList<>();

    private int concurrency = 1;

    private int timeoutMillis = 3000;

    private String username;

    private String password;

    private int timeToLiveSeconds = 3600;

    private int timeToIdleSeconds = 3600;

    private byte serializeMethod = PredefinedSerializeMethod.FST.getTag();

    {
        this.addresses.add(new ServerAddress("localhost", 11211));
    }

    public List<ServerAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<ServerAddress> addresses) {
        this.addresses = addresses;
    }

    public byte getSerializeMethod() {
        return serializeMethod;
    }

    public void setSerializeMethod(byte serializeMethod) {
        this.serializeMethod = serializeMethod;
    }

    public int getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

    public void setTimeToLiveSeconds(int timeToLiveSeconds) {
        this.timeToLiveSeconds = timeToLiveSeconds;
    }

    public int getTimeToIdleSeconds() {
        return timeToIdleSeconds;
    }

    public void setTimeToIdleSeconds(int timeToIdleSeconds) {
        this.timeToIdleSeconds = timeToIdleSeconds;
    }

    public int getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
