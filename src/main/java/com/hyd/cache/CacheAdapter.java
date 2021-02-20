package com.hyd.cache;

/**
 * 缓存通用接口
 */
public interface CacheAdapter {

    /**
     * 获取缓存配置
     *
     * @return 缓存配置
     */
    CacheConfiguration getConfiguration();

    /**
     * 获取缓存值
     *
     * @param key 缓存键
     *
     * @return 缓存值
     */
    Object get(String key) throws CacheException;

    /**
     * 设置缓存值
     *
     * @param key     缓存键
     * @param value   缓存值
     * @param forever 是否永久保存
     */
    void put(String key, Object value, boolean forever) throws CacheException;

    /**
     * 设置缓存值
     *
     * @param key               缓存键
     * @param value             缓存值
     * @param timeToLiveSeconds 本条缓存的保存时长（秒）
     */
    void put(String key, Object value, int timeToLiveSeconds) throws CacheException;

    /**
     * 删除缓存
     *
     * @param key 缓存键
     */
    void delete(String key) throws CacheException;

    /**
     * 清空当前缓存
     */
    void clear() throws CacheException;

    /**
     * 关闭缓存客户端对象，释放相关资源
     */
    void dispose() throws CacheException;

    void touch(String key);
}
