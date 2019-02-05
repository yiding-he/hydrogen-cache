package com.hyd.cache;

import com.hyd.cache.serialization.PredefinedSerializeMethod;
import org.apache.commons.lang3.StringUtils;

/**
 * 缓存配置。这个接口之所以没有任何方法，是因为不同的缓存库有不同的
 * 配置类，且 {@link CacheAdapterFactory} 对缓存配置没有要求特定的方法。
 */
public interface CacheConfiguration {

    default String getType() {
        return StringUtils.removeEnd(this.getClass().getSimpleName(), "Configuration");
    }

    /**
     * CacheAdapter 如果支持可选的序列化类型，则可以通过本方法
     * 获取用户选择的序列化类型。
     * <p>
     * 缺省情况下本方法返回 {@link PredefinedSerializeMethod#FST}
     *
     * @return 用户选择的序列化类型
     */
    default byte getSerializeMethod() {
        return PredefinedSerializeMethod.FST.getTag();
    }
}
