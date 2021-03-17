package com.ranhy.framework.manatee.cache.client.core;

import java.lang.reflect.Method;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 15:03
 * @description：cacheMap key
 */

@FunctionalInterface
public interface KeyGenerator {

    /**
     * 获取执行方法唯一key对象
     * @param target
     * @param method
     * @param params
     * @return
     */
    Object generate(Object target, Method method, Object... params);

}