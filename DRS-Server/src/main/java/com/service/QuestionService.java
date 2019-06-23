package com.service;

import com.alibaba.fastjson.JSON;
import com.bean.QuestionCacheHandler;
import com.dao.QuestionDao;
import com.entry.QuestionBodyDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author monetto
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private QuestionCacheHandler questionCacheHandler;

    public List<QuestionBodyDO> getQuestionByQid() {
        // todo Get Question ID from MySQL
        List<Integer> questionIdList = Arrays.asList(1, 3, 5, 6);
        // Final json data to front

        HashMap<Integer, Object> cacheGetResult = questionCacheHandler.get(questionIdList);
        List<Integer> needGetFromDatabase = (List) cacheGetResult.get(-1);
        Map<Integer, String> cachedQuestionJsonData = (Map) cacheGetResult.get(1);
        if (needGetFromDatabase.size() != 0) {
            List<HashMap> databaseGetResult = questionDao.getFrontQuestionDataByQid(needGetFromDatabase);
            for (HashMap frontJsonData : databaseGetResult) {
                Integer qid = (Integer) frontJsonData.get("qid");
                String questionJsonData = JSON.toJSONString(frontJsonData);
                cachedQuestionJsonData.put(qid, questionJsonData);
                questionCacheHandler.put(qid, questionJsonData);
            }
        }

        List returnData = new LinkedList();
        for (Integer qid : questionIdList) {
            // todo 修改fastjson 去掉\\
            String data = cachedQuestionJsonData.get(qid);
            returnData.add(JSON.parse(data));
        }

        return returnData;
    }


}
