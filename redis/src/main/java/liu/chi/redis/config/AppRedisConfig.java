/*
 * Copyright (c) 2018. The author is liu
 */

package liu.chi.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;
import java.util.List;

/**
 * @desc:
 * @author：liuchi
 * @date： 2018/02/03 17:22
 */
@Configuration
public class AppRedisConfig {
    private RedisProperties property;

    @Autowired
    public AppRedisConfig(RedisProperties property) {
        this.property = property;
    }

    /**
     * @return redis 连接工厂
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();

        //单机配置
        factory.getStandaloneConfiguration().setPort(property.getPort());
        factory.getStandaloneConfiguration().setHostName(property.getHost());
//        return factory;


        //主从配置
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration() .master("mymaster")
                .sentinel("127.0.0.1", 26379) .sentinel("127.0.0.1", 26380);
//        return new JedisConnectionFactory(sentinelConfig);

        //集群配置(redis cluser采用哈希分槽)(redis集群一半节点为从节点)
        List<String> list = Arrays.asList("127.0.0.1:6379", "127.0.0.1:6380", "127.0.0.1:6381");
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(list);
        return new JedisConnectionFactory(clusterConfiguration);
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        //指定序列化，美化key
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    @Qualifier("stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }


}
