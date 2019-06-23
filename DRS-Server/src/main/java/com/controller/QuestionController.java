package com.controller;

import com.alibaba.fastjson.JSON;
import com.bean.RequestResult;
import com.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author monetto
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ResponseBody
    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public String getQuestionJsonData(String name) {
        int serviceCode = 200;
        String msg = "Success";
        Object jsonData = null;
//        try {
            jsonData = questionService.getQuestionByQid();
//        } catch (Exception e) {
//
//            serviceCode = 500;
//            msg = "Service Error!";
//        }
        return new RequestResult(serviceCode, msg, jsonData).toString();
    }

}
