package com.ranhy.framework.manatee.cache.client.store;

import com.ranhy.framework.manatee.cache.client.constant.CacheStatusEnum;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 15:03
 * @description：缓存存储类
 */

@Data
public class ConcurrentMapCache implements  Cache{

    private final String name;

    private final ConcurrentMap<Object, Object> store;

    private volatile CacheStatusEnum status;

    /**
     * 最近一次清除时间
     */
    private volatile long LastPurgeTime = System.currentTimeMillis();

    public ConcurrentMapCache(String name) {
        this(name, new ConcurrentHashMap<Object, Object>(256) , CacheStatusEnum.EFFECTIVE );
    }

    public ConcurrentMapCache(String name , CacheStatusEnum status) {
        this(name, new ConcurrentHashMap<Object, Object>(256) ,status );
    }


    public ConcurrentMapCache(String name, ConcurrentMap<Object, Object> store , CacheStatusEnum status) {
        this.name = name;
        this.store = store;
        this.status = status;
    }

    @Override
    public Object get(Object key) {
        return this.store.get(key);
    }

    @Override
    public void put(Object key, Object value) {
              this.store.put(key,value);
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        return  this.store.put(key,value);
    }

    @Override
    public void clear() {
        this.store.clear();
    }

    @Override
    public void updateStatus(CacheStatusEnum statusEnum) {
        this.setStatus(statusEnum);
    }
}