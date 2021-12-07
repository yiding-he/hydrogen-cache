package com.hyd.cache.caches.cache2k;

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
        // "The expiry happens via a timer event and may lag approximately one second by default"
        config.setExpireAfterWriteMillis(1000);

        Cache cache = new Cache(config);
        String key = "key";
        String value = "value";

        cache.delete(key);
        cache.put(key, value);
        assertNotNull(cache.get(key));

        Thread.sleep(3000);
        assertNull(cache.get(key));
    }
    @Test(expected = NullPointerException.class)
    public void testNullConfiguration() throws Exception {
        new Cache2kAdapter(null);
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

    @Test
    public void getConfiguration() {
        Cache2kConfiguration config = new Cache2kConfiguration();
        config.setEntryCapacity(1000);
        config.setExpireAfterWriteMillis(10000);

        Cache cache = new Cache(config);
        assertEquals(config, cache.getConfiguration());
    }

    @Test
    public void putWithTTL() throws Exception {
        Cache cache = new Cache(new Cache2kConfiguration());
        cache.put("key", "value", 1);
        Thread.sleep(2000);
        assertEquals("value", cache.get("key"));
    }

    @Test
    public void delete() throws Exception {
        Cache cache = new Cache(new Cache2kConfiguration());
        cache.put("key", "value", 1);
        assertEquals("value", cache.get("key"));
        cache.delete("key");
        assertNull(cache.get("key"));
    }

    @Test
    public void clear() throws Exception {
        Cache cache = new Cache(new Cache2kConfiguration());
        cache.put("key", "value", 1);
        assertEquals("value", cache.get("key"));
        cache.clear();
        assertNull(cache.get("key"));
    }

    @Test(expected = org.cache2k.core.CacheClosedException.class)
    public void dispose() throws Exception {
        Cache cache = new Cache(new Cache2kConfiguration());
        cache.put("key", "value", 1);
        assertEquals("value", cache.get("key"));
        cache.getCacheAdapter().dispose();
        cache.get("key");  // throws Exception
    }

    // Just for coverage
    @Test
    public void testTouch() throws Exception {
        Cache cache = new Cache(new Cache2kConfiguration());
        cache.put("key", "value", 1);
        cache.touch("key");  // Nothing will happen.
    }
}
