<p>
  <a href="https://github.com/996icu/996.ICU/blob/master/LICENSE">
    <img alt="996icu" src="https://img.shields.io/badge/license-NPL%20(The%20996%20Prohibited%20License)-blue.svg">
  </a>
  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
</p>

假设你的项目出现架构调整，需要将原本的进程内缓存（例如 Caffeine）改为远程缓存（例如 Redis）时，需要改动多少类多少方法呢？

# hydrogen-cache

hydrogen-cache 是一个为不同种类的缓存提供统一 API 的类库。其目的是方便项目在本地缓存和远程缓存之间切换。

hydrogen-cache 支持以下底层实现：

- 本地缓存：
  - EhCache
  - Cache2k
  - caffeine
- 远程缓存：
  - memcached
  - redis

你的项目可以在开发过程中使用本地缓存，而在生产环境上使用远程缓存，无需修改代码，只需修改配置即可。

当前分支的最新版本是 3.1.0-SNAPSHOT

下面是一个使用例子：

```java
// 创建一个 com.hyd.cache.Cache 对象
Cache cache = new Cache(new EhCacheConfiguration());

// 简单存取
cache.put("name", queryName());
System.out.println("name: " + cache.get("name"));

// 取缓存时指定查询方法，当缓存没有时调用该方法填充缓存并返回
User user = cache.get("user", () -> queryUser());

// 异步获取缓存
// 这个例子中当 60 秒超时后，再次查询缓存会触发后台异步获取，
// 获取期间仍然返回当前的缓存内容，获取成功后再次查询会返回新值。
int pageDataExpirySeconds = 60;
PageData pageData = cache.getAsync(
        "page-data", pageDataExpirySeconds, () -> queryPageData());
```

## 创建 `Cache` 对象

通过下面几种方式之一来创建：

```java
// com.hyd.cache.Cache
new Cache(new Cache2kConfiguration());
new Cache(new CaffeineConfiguration());
new Cache(new EhCacheConfiguration());
new Cache(new MemcachedConfiguration());
new Cache(new RedisConfiguration());
// 更多缓存支持添加中...
```

## Spring Boot 中自动初始化

在 Spring Boot 中可以添加多个缓存配置，然后用 `Caches` 类获取各自的 Cache 对象。下面是一个例子：

```properties
# application.properties

# memcached instance
hydrogen-cache.memcached.REMOTE.host=localhost
hydrogen-cache.memcached.REMOTE.port=11211
hydrogen-cache.memcached.REMOTE.time-to-live-seconds=3600

# cache2k instances
hydrogen-cache.cache2k.LOCAL1.entry-capacity=10000
hydrogen-cache.cache2k.LOCAL2.entry-capacity=10000
```

无需额外编码，即可在代码里面使用缓存对象：

```java
import com.hyd.cache.springboot.Caches;
import com.hyd.cache.Cache;

public class CacheDemo {
    
    @Autowired
    private Caches caches;
    
    public void func() {
        Cache remoteCache = caches.get("REMOTE");
        Cache localCache1 = caches.get("LOCAL1");
        Cache localCache2 = caches.get("LOCAL2");
    }
}
```

当然你也可以在 `@Configuration` 类中定义单独的 Cache：

```java
import com.hyd.cache.springboot.Caches;
import com.hyd.cache.Cache;

@Configuration
public class Conf {
    
    @Bean
    public Cache remoteCache(Caches caches) {
        return caches.get("REMOTE");
    }
    
    @Bean
    public Cache localCache1(Caches caches) {
        return caches.get("LOCAL1");
    }
    
    @Bean
    public Cache localCache2(Caches caches) {
        return caches.get("LOCAL2");
    }
}
```

### Redis 序列化方式

hydrogen-cache 使用 Redis 时默认使用二进制序列化方式，但是这对文字客户端不友好。如果需要以字符方式序列化（JSON），则可以像下面这样：

```java
RedisConfiguration c = new RedisConfiguration();
c.setSerializeMethod(PredefinedSerializeMethod.JSON.getTag());
```

或者在 application.properties 当中指定

    hydrogen-cache.redis.[cache-name].serialize-method=1
