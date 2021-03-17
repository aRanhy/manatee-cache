package com.ranhy.framework.manatee.cache.client.core;



import java.lang.reflect.Method;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 15:03
 * @description：cacheMap key
 */

@SuppressWarnings("serial")
public class CacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        String targetName=target.getClass().getName()+"."+method.getName();
        return new CacheSimpleKey(targetName, params);
    }

}