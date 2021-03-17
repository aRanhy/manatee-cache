package com.ranhy.framework.manatee.cache.client.function; 
/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 17:39
 * @description：业务处理方法类
 */
@FunctionalInterface
public interface OperationCallback {

	Object call() throws Throwable;
}
 