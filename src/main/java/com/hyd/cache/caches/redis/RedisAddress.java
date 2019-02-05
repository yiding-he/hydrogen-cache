package com.hyd.cache.caches.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisAddress {

    private String host;

    private int port;

    private String password;
}
