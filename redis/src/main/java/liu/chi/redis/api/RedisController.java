package liu.chi.redis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redisTemplate 与spring结果缓存
 * @author liuchi
 * @date 2018-08-14 14:30
 */
@RestController
@CacheConfig(cacheNames = "App")//该注解必须添加，会作为该方法所有的缓存key头 AppRedisCache:cacheOne
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * key必须是变量,不能直接指定为字符串
     * 使用的是:RedisTemplate
     */
    @RequestMapping(value = "/cache")
    @Cacheable(key = "#root.method.name", unless = "#result == null")
    public String cacheMethod() {
        System.out.println("没有缓存cacheOne");
        return "cacheOne..";
    }


    @RequestMapping("/template")
    public String opsForValue() {
        redisTemplate.opsForValue().set("string", "哈哈");
        redisTemplate.opsForValue().set("temp", "哈哈", 1200);
        return "成功";
    }

    @RequestMapping("/stringTemplate")
    public String opsForValue2() {
        stringRedisTemplate.opsForValue().set("00", "汉字正常吗?");
        return "请查看redis,key为00的值";
    }

    @RequestMapping("/getAll")
    public void getAll() {
        System.out.println("App.cacheMethod" + redisTemplate.opsForValue().get("App::cacheMethod"));
        System.out.println("string: " + redisTemplate.opsForValue().get("string"));
        System.out.println("temp: " + redisTemplate.opsForValue().get("temp"));
        System.out.println("00: " + redisTemplate.opsForValue().get("00"));
    }

}
