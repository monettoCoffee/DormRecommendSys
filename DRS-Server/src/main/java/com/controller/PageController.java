package com.controller;

import com.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author monetto
 */
@Controller
public class PageController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/question")
    public String questionIndex() {
        return "question";
    }
}
