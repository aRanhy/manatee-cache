package com.ranhy.framework.manatee.cache.client.core;

import com.ranhy.framework.manatee.cache.client.constant.CacheStatusEnum;
import com.ranhy.framework.manatee.cache.client.properties.ManateeCacheProperties;
import com.ranhy.framework.manatee.cache.client.store.Cache;
import com.ranhy.framework.manatee.cache.client.store.ConcurrentMapCacheFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentMap;
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

            ConcurrentMap<String, Cache>  cacheMap = cacheFactory.getCacheMap();

            log.info("Cache-ExamineSync-Thread 线程已正常启动");
            while (!closed.get()) {
                try {
                    Thread.sleep(SECONDS.toMillis(properties.getCacheExamineInterval()) );

                    cacheMap.values().forEach(cache -> {

                        //删除状态时长超过指定时间未更新是，自动更新
                        if(cache.getStatus().equals(CacheStatusEnum.DELETEING) && System.currentTimeMillis()- cache.getLastPurgeTime() > properties.getCacheResetInterval()){
                            cache.setLastPurgeTime(System.currentTimeMillis());
                            cache.clear();
                            cache.updateStatus(CacheStatusEnum.EFFECTIVE);
                        }
                    });

                    //检测逻辑
                }catch (Exception e){
                    log.error(e.getMessage());
                }

            }

        },"Cache-ExamineSync-Thread");

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
