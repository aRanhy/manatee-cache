
package com.ranhy.framework.manatee.cache.client.annotation;

import com.ranhy.framework.manatee.cache.client.CacheAutoConfiguration;
import com.ranhy.framework.manatee.cache.client.properties.ManateeCacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 11:24
 * @description：cache enable
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableConfigurationProperties(ManateeCacheProperties.class)
@Import(value = {CacheAutoConfiguration.class})
public @interface EnableManateeCache {

}
