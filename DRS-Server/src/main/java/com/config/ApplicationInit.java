package com.config;

import com.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * @author monetto
 */
@Component
public class ApplicationInit implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${application.cache.useRedisCache}")
    private boolean useRedisCache;
    @Value("${application.cache.clearRedisCacheAtStartup}")
    private boolean clearRedisCacheAtStartup;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private QuestionDao questionDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        clearRedisCache();
        initRedisCache();
        System.out.println("DRS: Server Module Boot Successful!");
    }

    public void clearRedisCache() {
        if (clearRedisCacheAtStartup && useRedisCache) {
            this.redisTemplate.execute(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) {
                    connection.flushDb();
                    return null;
                }
            });
        }
    }

    public void initRedisCache() {
        if (useRedisCache) {
            String localAddress = "Unknown";
            try {
                localAddress = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            redisTemplate.opsForHash().putIfAbsent("dorm_question", "init", localAddress);
        }
    }

}
