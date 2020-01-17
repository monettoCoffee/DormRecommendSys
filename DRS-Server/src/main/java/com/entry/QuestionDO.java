package com.entry;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author monetto
 */
public class QuestionDO {
    private Integer autoWeight;
    private Integer qid;
    private Integer chooseType;
    private String tips;
    private String introduction;
    private Integer questionType;
    private Integer did;
    private Integer displayType;
    private String optionJson;
    private List<String> option;
    private Double weight;

    public Integer getAutoWeight() {
        return autoWeight;
    }

    public void setAutoWeight(Integer autoWeight) {
        this.autoWeight = autoWeight;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public Integer getChooseType() {
        return chooseType;
    }

    public void setChooseType(Integer chooseType) {
        this.chooseType = chooseType;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    public String getOptionJson() {
        return optionJson;
    }

    public void setOptionJson(String optionJson) {
        this.optionJson = optionJson;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }



    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
