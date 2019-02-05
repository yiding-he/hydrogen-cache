package com.hyd.cache;

import com.hyd.cache.caches.cache2k.Cache2kAdapter;
import com.hyd.cache.caches.cache2k.Cache2kConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AsyncCacheLoaderTest {

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAsyncLoadingWithException() throws Exception {
        Cache2kAdapter cache = new Cache2kAdapter(new Cache2kConfiguration());
        assertNull(cache.get("key"));

        AsyncCacheLoader.addTask("key", cache, () -> {
            sleep(2000);
            throw new RuntimeException("zzzzz");
        }, 5000);  // Exception will be printed but not thrown
    }

    @Test
    public void testAsyncLoading() throws Exception {
        Cache2kAdapter cache = new Cache2kAdapter(new Cache2kConfiguration());
        assertNull(cache.get("key"));

        AsyncCacheLoader.addTask("key", cache, () -> {
            sleep(2000);
            System.out.println("key = value");
            return "value";
        }, 5000);

        System.out.println("Queued Keys: " + AsyncCacheLoader.getQueuedKeys());

        AsyncCacheLoader.addTask("key", cache, () -> {
            sleep(2000);
            System.out.println("key = value--");
            return "value--";
        }, 5000);  // This task should be ignored

        sleep(2500);
        assertEquals("value", ((Element)cache.get("key")).getValue());

        System.out.println("Queued Keys: " + AsyncCacheLoader.getQueuedKeys());
        AsyncCacheLoader.addTask("key", cache, () -> {
            sleep(2000);
            System.out.println("key = value2");
            return "value2";
        }, 5000);

        assertEquals("value", ((Element)cache.get("key")).getValue());

        sleep(3000);  // first value expired
        System.out.println("Queued Keys: " + AsyncCacheLoader.getQueuedKeys());
        assertEquals("value2", ((Element)cache.get("key")).getValue());
    }
}