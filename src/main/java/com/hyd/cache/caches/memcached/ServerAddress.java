package com.hyd.cache.caches.memcached;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerAddress {

    private String host;

    private int port;
}
