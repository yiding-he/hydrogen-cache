package com.hyd.cache.caches.redis;

import com.hyd.cache.Cache;
import com.hyd.cache.bean.User;
import com.hyd.cache.serialization.PredefinedSerializeMethod;
import org.junit.Test;

public class RedisMultipleSerializeMethodTest {

    @Test
    public void testMultipleSerializeMethod() throws Exception {

        RedisConfiguration c = new RedisConfiguration();
        c.setDisposeOnDeserializationFailure(false);
        c.setSerializeMethod(PredefinedSerializeMethod.JSON.getTag());
        Cache cache = new Cache(c);

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("pass1");
        cache.put("user1", user1);


        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("pass2");
        cache.put("user2", user2);

        System.out.println((Object) cache.get("user1"));
        System.out.println(cache.get("user1").getClass());
        System.out.println((Object) cache.get("user2"));
        System.out.println(cache.get("user2").getClass());
    }
}
