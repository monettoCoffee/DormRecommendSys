package com.controller;

import com.bean.RequestResult;
import com.dao.QuestionPlanDao;
import com.service.QuestionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author monetto
 */
@Controller
public class TestController {

    @Autowired
    private QuestionPlanService questionPlanService;

    @ResponseBody
    @RequestMapping("/test")
    public String loginIndex(HttpServletRequest req) {
        questionPlanService.complateChosen("1");
        return RequestResult.success().toString();
    }
}
