package com.service;

import com.alibaba.fastjson.JSON;
import com.dao.QuestionPlanDao;
import com.entry.AccountDO;
import com.entry.QuestionPlanDO;
import com.entry.StudentStatusAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class QuestionPlanService {

    @Autowired
    private QuestionPlanDao questionPlanDao;

    public boolean complateChosen(String qid) {
//        List<QuestionPlanDO> incompleteQuestionPlan = questionPlanDao.getIncompleteQuestionPlan();
        return false;
    }

    public void addPlanToStudent(List<StudentStatusAO> studentStatusAOS) {
        HashMap<String, Integer> planPidMap = new HashMap<>();
        for (StudentStatusAO ao: studentStatusAOS) {
            String planName = ao.getPlanName();
            if (planName == null || "".equals(planName)) {
                continue;
            }
            QuestionPlanDO questionPlanDO = questionPlanDao.getQuestionPlanByPlanName(planName);
            if (questionPlanDO == null) {
                questionPlanDO = new QuestionPlanDO();
                questionPlanDO.setPlanName(planName);
                questionPlanDO.setDescription("");
                questionPlanDO.setDone(0);
                this.addPlanToDatabase(questionPlanDO);
            }
            planPidMap.put(planName, questionPlanDO.getPid());
        }

        for(StudentStatusAO info: studentStatusAOS) {
            System.out.println(JSON.toJSONString(info));
            AccountDO accountDO = new AccountDO();
            accountDO.setUid(info.getUid());
            if (null == info.getPlanName() || "".equals(info.getPlanName())) {
                accountDO.setPid(0);
            } else {
                accountDO.setPid(planPidMap.get(info.getPlanName()));
            }
            questionPlanDao.addPlanToStudent(accountDO);
        }
    }

    public Integer addPlanToDatabase(QuestionPlanDO questionPlanDO) {
        questionPlanDao.addNewQuestionPlan(questionPlanDO);
        return questionPlanDO.getPid();
    }

    public List<HashMap> getIncomplateQuestionPlan() {
        List<HashMap> questionPlanDOS = questionPlanDao.getIncompleteQuestionPlan();
        return questionPlanDOS;
    }

    public void updateQuestionPlan(List<QuestionPlanDO> questionPlanDOS) {
        for (QuestionPlanDO questionPlanDO: questionPlanDOS) {
            System.out.println(questionPlanDO.toString());
            questionPlanDO.setQidJson(JSON.toJSONString(questionPlanDO.getQid()));
            questionPlanDao.updateQuestionPlan(questionPlanDO);
        }
    }
}
