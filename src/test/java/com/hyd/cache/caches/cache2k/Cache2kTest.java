package com.hyd.cache.caches.cache2k;

import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class Cache2kTest {

    @Test
    public void testGetSet() throws Exception {
        Cache<Object, Object> cache = Cache2kBuilder.forUnknownTypes().build();
        cache.put("name", "value");
        Assert.assertEquals("value", cache.get("name"));
    }

    @Test
    public void testExpiry() throws Exception {
        Cache<Object, Object> cache = Cache2kBuilder.forUnknownTypes()
                .expireAfterWrite(1000, TimeUnit.MILLISECONDS).build();
        cache.put("name", "value");
        Thread.sleep(3000);
        Assert.assertNull(cache.get("name"));
    }
}
