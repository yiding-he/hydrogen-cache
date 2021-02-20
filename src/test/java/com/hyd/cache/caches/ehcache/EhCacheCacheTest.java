package com.hyd.cache.caches.ehcache;

import com.hyd.cache.Cache;
import org.junit.Test;

import static org.junit.Assert.*;

public class EhCacheCacheTest {

    @Test
    public void testGetSet() throws Exception {
        Cache cache = new Cache(new EhCacheConfiguration());
        String key = "key";
        String value = "value";

        cache.put(key, value);
        assertNotNull(cache.get(key));
        assertEquals(value, cache.get(key));

        String invalidKey = "INVALID_KEY_" + System.currentTimeMillis();
        assertNull(cache.get(invalidKey));
    }

    @Test
    public void testExpiry() throws Exception {
        EhCacheConfiguration config = new EhCacheConfiguration();
        config.setTimeToLiveSeconds(1);

        Cache cache = new Cache(config);
        String key = "key";
        String value = "value";

        cache.put(key, value);
        assertNotNull(cache.get(key));

        Thread.sleep(2100);
        assertNull(cache.get(key));
    }

    @Test
    public void testMultipleCache() throws Exception {
        Cache cache1 = new Cache(new EhCacheConfiguration());
        Cache cache2 = new Cache(new EhCacheConfiguration());
        Cache cache3 = new Cache(new EhCacheConfiguration());

        String key = "key";
        String value = "value";

        cache1.put(key, value);
        assertNotNull(cache1.get(key));
        assertEquals(value, cache1.get(key));
        assertNull(cache2.get(key));
        assertNull(cache3.get(key));
    }
}
