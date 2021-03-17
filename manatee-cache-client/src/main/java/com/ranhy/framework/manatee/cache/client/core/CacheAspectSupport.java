package com.ranhy.framework.manatee.cache.client.core;

import com.ranhy.framework.manatee.cache.client.annotation.Cacheable;
import com.ranhy.framework.manatee.cache.client.constant.CacheStatusEnum;
import com.ranhy.framework.manatee.cache.client.function.OperationCallback;
import com.ranhy.framework.manatee.cache.client.store.Cache;
import com.ranhy.framework.manatee.cache.client.store.ConcurrentMapCache;
import com.ranhy.framework.manatee.cache.client.store.ConcurrentMapCacheFactory;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 15:03
 * @description：代理类
 */
@AllArgsConstructor
public class CacheAspectSupport   {

    private ConcurrentMapCacheFactory factory;

    private KeyGenerator keyGenerator;

     public Object execute(OperationCallback callback, Object object, Method method , Object[] args) throws Throwable {

          String cacheName =   Optional.ofNullable(method.getAnnotation(Cacheable.class)).map(Cacheable::cacheName).orElse("");

          if(! StringUtils.isEmpty(cacheName)){

             Cache cache =  factory.getCache(cacheName) ;
             if(Objects.isNull(cache)){
                 cache = factory.putIfAbsent(cacheName,new ConcurrentMapCache(cacheName));
             }
             Object key = keyGenerator.generate(object,method,args);
             //判断缓存是否有效
             if(cache.getStatus().equals(CacheStatusEnum.EFFECTIVE)){

                 Object value =  cache.get(key);

                 if(Objects.isNull(value)){
                     value = cache.putIfAbsent(key,callback.call());
                 }
                 return value;
             }
          }

          return callback.call();
     }


}