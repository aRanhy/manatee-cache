package com.ranhy.framework.manatee.cache.client.core;

import com.ranhy.framework.manatee.cache.client.properties.ManateeCacheProperties;
import com.ranhy.framework.manatee.cache.client.store.ConcurrentMapCacheFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.concurrent.TimeUnit.SECONDS;

@RequiredArgsConstructor
@Slf4j
public class CacheExamineSync implements CacheSync{


    final AtomicBoolean closed = new AtomicBoolean(false);
    private Thread cacheSyncThread = null;
    final ManateeCacheProperties properties;
    final ConcurrentMapCacheFactory cacheFactory;

    @Override
    public void start() {
        cacheSyncThread =new Thread(()->{

            log.info("Acl-ConfigSync-Thread 线程已正常启动");
            while (!closed.get()) {
                try {
                    Thread.sleep(SECONDS.toMillis(properties.getCacheSyncInterval()) );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    //检测逻辑
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        },"Acl-ConfigSync-Thread");

        cacheSyncThread.setDaemon(true);
        cacheSyncThread.start();
    }

    @Override
    public void stop() {

        if(!closed.get() && cacheSyncThread !=null){
            closed.set(true);
        }
    }
}
