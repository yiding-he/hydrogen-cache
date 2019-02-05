package com.hyd.cache.springboot;

import com.hyd.cache.CacheException;

public class AutoConfigurationException extends CacheException {

    public AutoConfigurationException() {
    }

    public AutoConfigurationException(String message) {
        super(message);
    }

    public AutoConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoConfigurationException(Throwable cause) {
        super(cause);
    }
}
