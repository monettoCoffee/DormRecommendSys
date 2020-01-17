package com.controller;

import com.alibaba.fastjson.JSON;
import com.bean.RequestResult;
import com.entry.QuestionAO;
import com.service.AccountService;
import com.service.QuestionService;
import com.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author monetto
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ResponseBody
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public String getQuestionJsonData(HttpServletRequest request) {
        String account = RequestUtil.getSessionByName(request, "uid");
        // todo 从 dorm question plan 里面获取尚未完成的计划
        accountService.getStudentByAccount(account);

        Object jsonData = questionService.getQuestionByQid(true);
        return new RequestResult(200, "Success", jsonData).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/add/question", method = RequestMethod.POST)
    public String addQuestionToDatabase(String paramJson1) {
        QuestionAO questionAO = JSON.parseObject(paramJson1, QuestionAO.class);
        questionService.addQuestionToDatabase(questionAO);
        return new RequestResult(200, "Success", null).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/delete/question", method = RequestMethod.POST)
    public String deleteQuestionFromDatabase(HttpServletRequest request, Integer qid) {
        String uid = RequestUtil.getSessionByName(request, "uid");
        if (!accountService.adminPermission(uid)) {
            return new RequestResult(2001, "Success", null).toString();
        }
        questionService.deleteQuestionFromDatabase(qid);
        return new RequestResult(2000, "Success", null).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/question/introduction", method = RequestMethod.POST)
    public String getAllQuestionIntroduction() {
        return new RequestResult(2000, "Success", questionService.getAllQuestionIntroduction()).toString();
    }

}
