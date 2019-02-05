package com.hyd.cache.springboot;

import com.hyd.cache.Cache;
import com.hyd.cache.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class Caches {

    private static final Logger LOG = LoggerFactory.getLogger(Caches.class);

    private Map<String, Cache> cacheMappings = new HashMap<>();

    public Caches(CacheAutoConfig configuration) {
        register(configuration.getCache2k());
        register(configuration.getCaffeine());
        register(configuration.getEhcache());
        register(configuration.getMemcached());
        register(configuration.getRedis());
    }

    public void forEach(BiConsumer<String, Cache> consumer) {
        this.cacheMappings.forEach(consumer);
    }

    private void register(Map<String, ? extends CacheConfiguration> configs) {
        if (configs != null) {
            configs.forEach(this::register);
        }
    }

    private void register(String name, CacheConfiguration config) {
        if (config.isTemplate()) {
            LOG.debug("Cache '" + name + "' is template.");
            return;
        }

        if (cacheMappings.containsKey(name)) {
            throw new AutoConfigurationException("Cache name '" + name + "' already exists.");
        }

        cacheMappings.put(name, new Cache(config));
        LOG.debug("Cache '" + name + "'[" + config.getType() + "] created.");
    }

    public Cache get(String name) {
        return cacheMappings.get(name);
    }
}
