package com.dao;

import com.entry.QuestionBodyDO;
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


    QuestionBodyDO getQuestionByQid(int qid);

    QuestionExtraDO getQuestionExtraByQid(int qid);

    QuestionSectionDO getQuestionSectionByQid(int qid);

    List<QuestionBodyDO> getQuestionListByQid(List<Integer> qid);

    List<QuestionExtraDO> getQuestionExtraListByQid(List<Integer> qid);

    List<QuestionSectionDO> getQuestionSectionListByQid(List<Integer> qid);

}
