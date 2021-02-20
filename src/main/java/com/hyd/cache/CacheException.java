package com.hyd.cache;

/**
 * Cache 相关异常
 */
public class CacheException extends RuntimeException {

    public static CacheException wrap(Exception e) {
        return e instanceof CacheException ? (CacheException) e : new CacheException(e);
    }

    public CacheException() {
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheException(Throwable cause) {
        super(cause);
    }
}
