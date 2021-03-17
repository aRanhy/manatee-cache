package com.ranhy.framework.manatee.cache.client.core;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author     ：hongyu.ran
 * @date       ： 2021/3/15 15:03
 * @description：cacheMap key
 */

@SuppressWarnings("serial")
public class CacheSimpleKey implements Serializable {

    private static final long serialVersionUID = 2662625531409588823L;

    private final Object[] params;

    private final int hashCode;

    private final String targetName;

    /**
     * Create a new {@link SimpleKey} instance.
     *
     * @param elements the elements of the key
     */
    public CacheSimpleKey(String targetName, Object... elements) {
        Assert.notNull(targetName, "targetName must not be null");
        this.targetName = targetName;
        if (elements == null) {
            this.hashCode = this.targetName.hashCode();
            this.params = new Object[0];
        } else {
            this.params = new Object[elements.length];
            System.arraycopy(elements, 0, this.params, 0, elements.length);
            this.hashCode = this.targetName.hashCode() + Arrays.deepHashCode(this.params);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this.params.length == 0) {
            return (this == obj || (obj instanceof CacheSimpleKey && this.targetName.equalsIgnoreCase(((CacheSimpleKey) obj).getTargetName())));
        } else {
            return (this == obj || (obj instanceof CacheSimpleKey && Arrays.deepEquals(this.params, ((CacheSimpleKey) obj).params)) && this.targetName.equalsIgnoreCase(((CacheSimpleKey) obj).getTargetName()));
        }
    }

    @Override
    public final int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return this.getTargetName() + " [" + StringUtils.arrayToCommaDelimitedString(this.params) + "]";
    }

    public Object[] getParams() {
        return params;
    }

    public String getTargetName() {
        return targetName;
    }

}