package com.hyd.cache.caches.memcached;

import com.hyd.cache.Cache;
import com.hyd.cache.bean.User;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MemcachedCacheTest {

    private Cache cache = new Cache(new MemcachedConfiguration());

    @Test
    public void testGetSet() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass1");
        cache.put("user", user);

        User cachedUser = cache.get("user");
        assertEquals(user.getUsername(), cachedUser.getUsername());
        assertEquals(user.getPassword(), cachedUser.getPassword());
    }

    @Test
    public void testExpire() throws Exception {
        cache.put("1_sec_key", "1_sec_value", 1);
        assertNotNull(cache.get("1_sec_key"));

        Thread.sleep(1500);
        assertNull(cache.get("1_sec_key"));
    }

    @Test
    public void testMultipleServer() throws Exception {
        MemcachedConfiguration config = new MemcachedConfiguration();
        config.setAddresses(Arrays.asList(
                new ServerAddress("localhost", 11211),
                new ServerAddress("localhost", 11212),
                new ServerAddress("localhost", 11213)
        ));

        Cache cache = new Cache(config);
        for (int i = 0; i < 1000; i++) {
            String key = "key:" + i;
            String value = "value:" + i;
            cache.put(key, value);
            assertEquals(value, cache.get(key));
        }
    }
}
