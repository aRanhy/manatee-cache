package com.ranhy.framework.manatee.cache.client.store;

import com.ranhy.framework.manatee.cache.client.constant.CacheStatusEnum;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 15:03
 * @description：缓存存储类
 */


public interface Cache {

    String getName();

    Object get(Object key);

    void put(Object key, Object value);

     Object putIfAbsent(Object key, Object value);

    void clear();

    void updateStatus(CacheStatusEnum statusEnum);

    CacheStatusEnum getStatus();

    long getLastPurgeTime();

    void setLastPurgeTime(long time);

}