package com.service;

import com.bean.QuestionCacheHandler;
import com.bean.RequestResult;
import com.dao.UserChosenDao;
import com.entry.UserChosenDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author monetto
 */
@Service
public class UserChosenService {
    @Autowired
    private UserChosenDao userChosenDao;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private QuestionCacheHandler questionCacheHandler;

    private static int id = 0;

    public String saveUserChosen(String chosen) {
        if (!calibrateUserChosen(chosen)) {
            return new RequestResult(201, "False", null).toString();
        }
        UserChosenDO userChosen = new UserChosenDO(chosen);
        //todo 从JWT中解析USER
        userChosen.setUid(id++);
        userChosen.setPid(1);
        userChosenDao.saveUserChosen(userChosen);
        return RequestResult.success().toString();
    }

    private boolean calibrateUserChosen(String chosen) {
        //todo 加入chosen校验
        return true;
    }


}
