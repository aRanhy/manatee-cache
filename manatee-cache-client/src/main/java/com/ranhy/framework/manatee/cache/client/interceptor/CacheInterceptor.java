package com.ranhy.framework.manatee.cache.client.interceptor;

import com.ranhy.framework.manatee.cache.client.core.CacheAspectSupport;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 15:03
 * @description：代理类
 */
@Data
@AllArgsConstructor
public class CacheInterceptor implements MethodInterceptor, Serializable {


    private CacheAspectSupport cacheAspectSupport;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Method method = methodInvocation.getMethod();
        Object[] args= methodInvocation.getArguments();
        return cacheAspectSupport.execute(methodInvocation::proceed,methodInvocation.getThis(),method,args);
    }



}