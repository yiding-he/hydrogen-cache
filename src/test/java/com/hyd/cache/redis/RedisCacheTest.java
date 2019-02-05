package com.hyd.cache.redis;

import com.hyd.cache.Cache;
import com.hyd.cache.bean.User;
import org.junit.Assert;
import org.junit.Test;

public class RedisCacheTest {

    @Test
    public void testGetSet() throws Exception {
        RedisConfiguration c = RedisConfiguration.singleServer("127.0.0.1", 6379);
        c.setTimeToLiveSeconds(60);

        Cache cache = new Cache(c);

        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass1");
        cache.put("user", user);

        User cachedUser = cache.get("user");
        Assert.assertEquals(user.getUsername(), cachedUser.getUsername());
        Assert.assertEquals(user.getPassword(), cachedUser.getPassword());
    }
}
