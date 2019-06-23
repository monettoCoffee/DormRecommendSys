package com.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.JobKOctets;
import java.util.*;

/**
 * @author monetto
 */
@Component
public class QuestionCacheHandler {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${application.cache.useLocalCache}")
    private boolean useLocalCache;
    @Value("${application.cache.useRedisCache}")
    private boolean useRedisCache;
    private WeakHashMap<Integer, String> cache = new WeakHashMap<>();

    public boolean useCache() {
        return useRedisCache || useLocalCache;
    }

    public HashMap<Integer, Object> get(List<Integer> allQid) {
        HashMap<Integer, Object> getResult = new HashMap<>();
        // Save the data where from cache
        HashMap<Integer, String> cachedQuestionJsonData = new HashMap<>();
        getResult.put(1, cachedQuestionJsonData);

        // 1. This is all redis cached question id
        Set allRedisCacheId = useRedisCache ? redisTemplate.opsForHash().keys("dorm_question") : null;

        LinkedList<String> needGetFromRedisCacheQid = new LinkedList<>();
        LinkedList<Integer> needGetFromDatabase = new LinkedList<>();
        getResult.put(-1, needGetFromDatabase);

        for (Integer qid : allQid) {
            String questionJsonData = getFromLocalCache(qid);
            if (questionJsonData != null) {
                System.out.println("ID: " + qid + " get from local cache");
                cachedQuestionJsonData.put(qid, questionJsonData);
            } else if (useRedisCache && allRedisCacheId.contains(qid.toString())) {
                System.out.println("ID: " + qid + " get from redis cache");
                // This question data can get from redis cache
                needGetFromRedisCacheQid.add(qid.toString());
            } else {
                System.out.println("ID: " + qid + " need get from db");
                needGetFromDatabase.add(qid);
            }
        }

        // 2. get cache from redis where the cache not in local cache
        List redisCacheList = redisTemplate.opsForHash().multiGet("dorm_question", (Collection) needGetFromRedisCacheQid);
        // 3. put cache into result
        int redisCacheListIndex = 0;
        for (String qid : needGetFromRedisCacheQid) {
            // multiGet return ArrayList so it can direct get through index
            String questionJsonData = redisCacheList.get(redisCacheListIndex++).toString();
            cachedQuestionJsonData.put(Integer.parseInt(qid), questionJsonData);
            putIntoLocalCache(Integer.parseInt(qid), questionJsonData);
        }

        return getResult;
    }

    public String get(Integer key) {
        String result = getFromLocalCache(key);
        return key == null ? getFromRedisCache(key) : result;
    }

    public void put(Integer key, String value) {
        putIntoLocalCache(key, value);
        putIntoRedisCache(key, value);
    }

    private String getFromRedisCache(Integer key) {
        return useRedisCache ? (String) redisTemplate.opsForHash().get("dorm_question", key) : null;
    }

    private List getFromRedisCache(List keys) {
        return useRedisCache ? redisTemplate.opsForHash().multiGet("dorm_question", keys) : null;
    }

    private void putIntoRedisCache(Integer key, String value) {
        if (useRedisCache) {
            redisTemplate.opsForHash().put("dorm_question", key.toString(), value);
        }
    }

    private String getFromLocalCache(Integer key) {
        return useLocalCache ? cache.get(key) : null;
    }

    private void putIntoLocalCache(Integer key, String value) {
        if (useLocalCache) {
            cache.put(key, value);
        }
    }

}
