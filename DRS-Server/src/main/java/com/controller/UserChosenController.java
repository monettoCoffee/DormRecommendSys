package com.controller;

import com.service.UserChosenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author monetto
 */
@Controller
public class UserChosenController {
    @Autowired
    private UserChosenService userChosenService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @ResponseBody
    @RequestMapping(value = "/chosen", method = RequestMethod.POST)
    public String receiveChosenResult(String chosen) {
        // todo 加入数据校验, 从Request里获取User
        return userChosenService.saveUserChosen(chosen);
    }


}
