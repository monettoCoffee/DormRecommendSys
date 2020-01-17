package com.controller;

import com.alibaba.fastjson.JSON;
import com.bean.RequestResult;
import com.entry.QuestionPlanDO;
import com.entry.StudentStatusAO;
import com.service.QuestionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author monetto
 */
@Controller
public class QuestionPlanController {

    @Autowired
    QuestionPlanService questionPlanService;

    @ResponseBody
    @RequestMapping(value = "/add/plan/student", method = RequestMethod.POST)
    public String addStudentPlan(String studentPlanJson) {
        System.out.println("/add/plan/student : " + studentPlanJson);
        List<StudentStatusAO> accountInfoAO = JSON.parseArray(studentPlanJson, StudentStatusAO.class);
        questionPlanService.addPlanToStudent(accountInfoAO);
        return new RequestResult(200, "Success", null).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/plan/info", method = RequestMethod.POST)
    public String getPlanInfo() {
        return new RequestResult(200, "Success", questionPlanService.getIncomplateQuestionPlan()).toString();
    }

    @ResponseBody
    @RequestMapping(value = "/add/plan/confirm", method = RequestMethod.POST)
    public String confirmPlan(String questionPlanJson) {
        List<QuestionPlanDO> questionPlanDOS = JSON.parseArray(questionPlanJson, QuestionPlanDO.class);
        questionPlanService.updateQuestionPlan(questionPlanDOS);
        return new RequestResult(200, "Success", questionPlanService.getIncomplateQuestionPlan()).toString();
    }


}
