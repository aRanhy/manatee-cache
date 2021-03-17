package com.ranhy.framework.manatee.cache.client.properties;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author	 hz18093501 hongyu.ran
 * @date	2019年8月8日
 */

@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(ManateeCacheProperties.PREFIX)
public class ManateeCacheProperties {

    public static final String PREFIX="manatee.cache";

    /**
     * 检测缓存状态间隙
     */
    private long cacheExamineInterval =SECONDS.toMillis(10);

    /**
     * 复位缓存间隙
     */
    private long cacheResetInterval = MINUTES.toMillis(1L);

}
