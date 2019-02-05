package com.hyd.cache;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 缓存配置。这个接口之所以没有任何方法，是因为不同的缓存库有不同的
 * 配置类，且 {@link CacheAdapterFactory} 对缓存配置没有要求特定的方法。
 */
public abstract class CacheConfiguration implements Serializable {

    /**
     * 表示这个配置是一个模板，可以重复使用，以创建多个 {@link CacheAdapter} 对象。
     */
    private boolean template;

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public String getType() {
        return StringUtils.removeEnd(this.getClass().getSimpleName(), "Configuration");
    }
}
