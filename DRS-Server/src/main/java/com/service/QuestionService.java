package com.service;

import com.alibaba.fastjson.JSON;
import com.bean.QuestionCacheHandler;
import com.dao.QuestionDao;
import com.entry.QuestionAO;
import com.entry.QuestionDO;
import com.entry.QuestionExtraDO;
import com.entry.QuestionSectionDO;
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

    public List<QuestionDO> getQuestionByQid() {
        return this.getQuestionByQid(false);
    }

    public List<QuestionDO> getQuestionByQid(boolean allQuestion) {
        // todo Get Question ID from MySQL
        List<Integer> questionIdList;
        if (allQuestion) {
            questionIdList = questionDao.getAllFrontQuestionQid();
        } else {
            questionIdList = Arrays.asList(1);
        }

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

    public void addQuestionToDatabase(QuestionAO questionAO) {
        String addQuestionType = questionAO.getAddQuestionType();
        QuestionDO questionDO = new QuestionDO();
        Integer displayType = 0;
        questionDO.setTips(questionAO.getAddQuestionTips());
        questionDO.setIntroduction(questionAO.getAddQuestionTitle());
        questionDO.setWeight(Double.parseDouble(questionAO.getAddQuestionDefaultWeight()));
        questionDO.setDid(0);
        questionDO.setOptionJson(JSON.toJSONString(questionAO.getAddQuestionChosenGroup()));
        if ("yes".equals(questionAO.getAddQuestionHasExtraAnswer())) {
            displayType = displayType | 1;
        }
        if ("yes".equals(questionAO.getAddQuestionChosenStress())) {
            displayType = displayType | 4;
        }
        if ("yes".equals(questionAO.getAddQuestionAutoWeightRatio())) {
            questionDO.setAutoWeight(1);
        } else {
            questionDO.setAutoWeight(0);
        }
        if ("one_chosen".equals(addQuestionType)) {
            questionDO.setQuestionType(1);
            questionDO.setChooseType(1);
            questionDO.setDisplayType(displayType);
            questionDao.addQuestionToDatabase(questionDO);
        } else if ("multi_chosen".equals(addQuestionType)) {
            questionDO.setQuestionType(2);
            displayType = displayType | 2;
            questionDO.setChooseType(Integer.parseInt(questionAO.getAddQuestionMultiChosenNumber()));
            questionDO.setDisplayType(displayType);
            questionDao.addQuestionToDatabase(questionDO);
        } else if ("section_chosen".equals(addQuestionType)) {
            questionDO.setQuestionType(3);
            displayType = displayType | 2;
            questionDO.setChooseType(Integer.parseInt(questionAO.getAddQuestionMultiChosenNumber()));
            questionDO.setDisplayType(displayType);
            questionDao.addQuestionToDatabase(questionDO);
            HashMap<String, Object> upParam = new HashMap<>();
            HashMap<String, Object> downParam = new HashMap<>();
            upParam.put("selectOption", questionAO.getAddQuestionUpSection());
            upParam.put("selectIntro", questionAO.getAddQuestionUpSelectIntro());
            downParam.put("selectOption", questionAO.getAddQuestionDownSection());
            downParam.put("selectIntro", questionAO.getAddQuestionDownSelectIntro());
            List<Object> sectionParam = new LinkedList<>();
            sectionParam.add(upParam);
            sectionParam.add(downParam);
            QuestionSectionDO questionSectionDO = new QuestionSectionDO();
            questionSectionDO.setSectionJson(JSON.toJSONString(sectionParam));
            questionSectionDO.setQid(questionDO.getQid());
            questionDao.addQuestionSectionToDatabase(questionSectionDO);
        }
        if ("yes".equals(questionAO.getAddQuestionHasExtraAnswer())) {
            QuestionExtraDO questionExtraDO = new QuestionExtraDO();
            questionExtraDO.setQid(questionDO.getQid());
            questionExtraDO.setExtIntro(questionAO.getAddQuestionExtraIntro());
            questionExtraDO.setExtPlaceholder(questionAO.getAddQuestionExtraPlaceholder());
            questionExtraDO.setExtDefaultValue(questionAO.getAddQuestionExtraDefaultValue());
            questionExtraDO.setExtAddButtonValue(questionAO.getAddQuestionExtraAddButtonValue());
            questionDao.addQuestionExtraToDatabase(questionExtraDO);
        }

    }

    public void deleteQuestionFromDatabase(Integer qid) {
        questionDao.deleteQuestionFromDatabase(qid);
        questionDao.deleteQuestionExtraFromDatabase(qid);
        questionDao.deleteQuestionSectionFromDatabase(qid);
    }

    public List<QuestionDO> getAllQuestionIntroduction() {
        return questionDao.getAllQuestionIntroduction();
    }

}
