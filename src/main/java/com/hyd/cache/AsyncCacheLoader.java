package com.hyd.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 后台加载缓存内容
 */
public class AsyncCacheLoader {

    private static final int MAX_WORKERS = Math.min(5, Runtime.getRuntime().availableProcessors());

    private static final List<String> QUEUED_KEYS = new ArrayList<>();

    private static final ThreadPoolExecutor POOL_EXECUTOR =
            new ThreadPoolExecutor(1, MAX_WORKERS, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public static List<String> getQueuedKeys() {
        return QUEUED_KEYS;
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(POOL_EXECUTOR::shutdownNow));
    }

    /**
     * 添加一个刷新任务到队列
     */
    public static void addTask(String key, CacheAdapter cache, Supplier<?> supplier, long expiry) {

        String queueKey = key + ":" + cache.hashCode();
        RefreshTask task = new RefreshTask(queueKey, key, cache, supplier, expiry);

        // 如果该 cacheKey 已经在队列中，则跳过
        synchronized (QUEUED_KEYS) {
            if (QUEUED_KEYS.contains(queueKey)) {
                return;
            }
        }

        QUEUED_KEYS.add(queueKey);
        POOL_EXECUTOR.submit(task);
    }

    ////////////////////////////////////////////////////////////

    /**
     * 执行刷新的任务
     */
    private static class RefreshTask implements Runnable {

        private static final Logger LOG = LoggerFactory.getLogger(RefreshTask.class);

        /**
         * 任务 key
         */
        private String queueKey;

        /**
         * 缓存 key
         */
        private String cacheKey;

        /**
         * 要保存到的缓存
         */
        private CacheAdapter cache;

        /**
         * 查询数据的方法
         */
        private Supplier<?> supplier;

        /**
         * 缓存超时时间（ms）
         */
        private long expiry;

        RefreshTask(String queueKey, String cacheKey, CacheAdapter cache, Supplier<?> supplier, long expiry) {
            this.queueKey = queueKey;
            this.cacheKey = cacheKey;
            this.cache = cache;
            this.supplier = supplier;
            this.expiry = expiry;
        }

        @Override
        public void run() {
            try {
                Object value = supplier.get();
                if (value != null) {
                    Element<Object> element = new Element<>(value);
                    element.setExpiry(expiry);
                    cache.put(cacheKey, element, true);
                }
            } catch (Exception e) {
                LOG.error("", e);
            } finally {
                QUEUED_KEYS.remove(queueKey);
            }
        }
    }

}
