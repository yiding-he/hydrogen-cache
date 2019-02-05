package com.hyd.cache;

import com.hyd.cache.ehcache.EhCacheConfiguration;
import org.junit.Test;

public class HydrogenCacheTest {

    @Test
    public void testGetAsync() throws Exception {
        EhCacheConfiguration conf = new EhCacheConfiguration();
        Cache cache = new Cache(conf);

        for (int i = 0; i < 30; i++) {
            Long value = cache.getAsync("key", 3, System::currentTimeMillis);
            System.out.println(value);
            Thread.sleep(1000);
        }
    }
}
