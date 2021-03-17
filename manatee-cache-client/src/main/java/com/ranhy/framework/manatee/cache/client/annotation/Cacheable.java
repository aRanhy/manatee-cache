
package com.ranhy.framework.manatee.cache.client.annotation;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 11:24
 * @description：
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cacheable {

    /**
     * Alias for {@link #cacheName}.
     */
    @AliasFor("cacheName")
    String value() default "";


    @AliasFor("value")
    String cacheName() default "";


}
 