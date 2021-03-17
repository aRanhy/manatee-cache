package com.ranhy.framework.manatee.cache.client.store;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 15:03
 * @description：缓存存储工厂
 */
public class ConcurrentMapCacheFactory implements CacheFactory {


    private final ConcurrentMap<String, Cache> cacheMap =  new ConcurrentHashMap<String, Cache>(16) ;


    @Override
    public   Cache getCache(String name) {
        return cacheMap.get(name);
    }

    @Override
    public void putCache(String name, Cache cache) {
        cacheMap.put(name, cache);
    }

    @Override
    public Cache putIfAbsent(String name, Cache cache) {
        return cacheMap.putIfAbsent(name,cache);
    }
}