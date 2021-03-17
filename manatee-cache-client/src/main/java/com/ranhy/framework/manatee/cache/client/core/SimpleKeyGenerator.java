package com.ranhy.framework.manatee.cache.client.core;



import java.lang.reflect.Method;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 15:03
 * @description：cacheMap key
 */

@SuppressWarnings("serial")
public class SimpleKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return generateKey(params);
    }

    /**
     * Generate a key based on the specified parameters.
     */
    public static Object generateKey(Object... params) {
        if (params.length == 0) {
            return  SimpleKey.EMPTY;
        }
        if (params.length == 1) {
            Object param = params[0];
            if (param != null && !param.getClass().isArray()) {
                return param;
            }
        }
        return new SimpleKey(params);
    }

}