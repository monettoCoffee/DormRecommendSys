package com.dao;

import com.entry.AccountDO;
import com.entry.QuestionPlanDO;
import com.entry.StudentStatusAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface QuestionPlanDao {

    List<HashMap> getIncompleteQuestionPlan();

    void addPlanToStudent(AccountDO ao);

    void addNewQuestionPlan(QuestionPlanDO questionPlanDO);

    QuestionPlanDO getQuestionPlanByPlanName(String planName);

    void updateQuestionPlan(QuestionPlanDO questionPlanDO);

}
