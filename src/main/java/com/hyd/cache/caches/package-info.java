/**
 * 如何添加一个自定义的实现：
 * 1、实现一个 {@link com.hyd.cache.CacheAdapter} 的子类；
 * 2、实现一个 {@link com.hyd.cache.CacheConfiguration} 的子类，在子类中加入一个静态块，当中调用
 * {@link com.hyd.cache.CacheAdapterFactory#register(Class, CacheAdapterCreator)}
 * 方法（可参考现有的类）
 */
package com.hyd.cache.caches;

import com.hyd.cache.CacheAdapterFactory.CacheAdapterCreator;