package com.ranhy.framework.manatee.cache.client.store;

import com.ranhy.framework.manatee.cache.client.constant.CacheStatusEnum;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 15:03
 * @description：缓存存储工厂接口
 */


public interface CacheFactory {

    Cache getCache(String name);

    void putCache(String name, Cache cache);

    Cache putIfAbsent(String name, Cache cache);

}