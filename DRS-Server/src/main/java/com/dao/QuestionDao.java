package com.dao;

import com.entry.QuestionDO;
import com.entry.QuestionExtraDO;
import com.entry.QuestionSectionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * @author monetto
 */
@Mapper
public interface QuestionDao {

    List<HashMap> getFrontQuestionDataByQid(List<Integer> qid);

    List<Integer> getAllFrontQuestionQid();

    QuestionDO getQuestionByQid(int qid);

    QuestionExtraDO getQuestionExtraByQid(int qid);

    QuestionSectionDO getQuestionSectionByQid(int qid);

    List<QuestionDO> getQuestionListByQid(List<Integer> qid);

    List<QuestionExtraDO> getQuestionExtraListByQid(List<Integer> qid);

    List<QuestionSectionDO> getQuestionSectionListByQid(List<Integer> qid);

    void addQuestionToDatabase(QuestionDO questionDO);

    void addQuestionSectionToDatabase(QuestionSectionDO questionSectionDO);

    void addQuestionExtraToDatabase(QuestionExtraDO questionExtraDO);

    void deleteQuestionFromDatabase(Integer qid);

    void deleteQuestionExtraFromDatabase(Integer qid);

    void deleteQuestionSectionFromDatabase(Integer qid);

    List<QuestionDO> getAllQuestionIntroduction();
}
