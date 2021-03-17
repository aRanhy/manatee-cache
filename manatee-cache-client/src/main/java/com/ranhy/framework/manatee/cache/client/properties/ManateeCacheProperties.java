package com.ranhy.framework.manatee.cache.client.properties;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static java.util.concurrent.TimeUnit.MINUTES;

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

    private long cacheSyncInterval = MINUTES.toSeconds(1L);

}
