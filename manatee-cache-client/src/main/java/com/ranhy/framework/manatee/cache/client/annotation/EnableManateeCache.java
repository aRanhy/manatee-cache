
package com.ranhy.framework.manatee.cache.client.annotation;

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
@Import(value = {InkfishAutoConfiguration.class})
public @interface EnableManateeCache {

}
