package com.hyd.cache.cache2k;

import com.hyd.cache.Cache;
import org.junit.Test;

import static org.junit.Assert.*;

public class Cache2KCacheTest {

    @Test
    public void testGetSet() throws Exception {
        Cache cache = new Cache(new Cache2kConfiguration());
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
        Cache2kConfiguration config = new Cache2kConfiguration();
        config.setExpireAfterWriteMillis(1000);

        Cache cache = new Cache(config);
        String key = "key";
        String value = "value";

        cache.put(key, value);
        assertNotNull(cache.get(key));

        Thread.sleep(1100);
        assertNull(cache.get(key));
    }

    @Test
    public void testMultipleCache() throws Exception {
        Cache cache1 = new Cache(new Cache2kConfiguration());
        Cache cache2 = new Cache(new Cache2kConfiguration());
        Cache cache3 = new Cache(new Cache2kConfiguration());

        String key = "key";
        String value = "value";

        cache1.put(key, value);
        assertNotNull(cache1.get(key));
        assertEquals(value, cache1.get(key));
        assertNull(cache2.get(key));
        assertNull(cache3.get(key));

    }
}
