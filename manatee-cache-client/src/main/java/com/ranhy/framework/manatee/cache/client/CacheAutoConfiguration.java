package com.ranhy.framework.manatee.cache.client;

import com.ranhy.framework.manatee.cache.client.core.*;
import com.ranhy.framework.manatee.cache.client.interceptor.CacheInterceptor;
import com.ranhy.framework.manatee.cache.client.properties.ManateeCacheProperties;
import com.ranhy.framework.manatee.cache.client.store.ConcurrentMapCacheFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/17 15:35
 * @description：装置bean
 */
@Configuration
@ConditionalOnProperty(prefix = ManateeCacheProperties.PREFIX, name = "enabled",matchIfMissing = true, havingValue = "true")
public class CacheAutoConfiguration {

    /**
     * 缓存代理拦截器
     * @param cacheAspectSupport
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    CacheInterceptor cacheInterceptor(CacheAspectSupport cacheAspectSupport){
        return  new CacheInterceptor(cacheAspectSupport);
    }

    /**
     * 状态复位定时器
     * @param properties 属性
     * @param cacheFactory 缓存工厂
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    CacheSync cacheSync(ManateeCacheProperties properties, ConcurrentMapCacheFactory cacheFactory){
        return new CacheExamineSync(properties, cacheFactory);
    }

    /**
     * 本地缓存工厂
     * @return
     */
    @Bean
    ConcurrentMapCacheFactory concurrentMapCacheFactory(){
        return new ConcurrentMapCacheFactory();
    }


    /**
     * 接口缓存key生成器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    KeyGenerator keyGenerator(){
        return new CacheKeyGenerator();
    }

    /**
     * 缓存接口切面处理逻辑类
     * @param cacheFactory
     * @param keyGenerator
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    CacheAspectSupport cacheAspectSupport(ConcurrentMapCacheFactory cacheFactory, KeyGenerator keyGenerator){
        return new CacheAspectSupport(cacheFactory,keyGenerator);
    }

}
