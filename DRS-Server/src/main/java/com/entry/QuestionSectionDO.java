package com.entry;

import com.alibaba.fastjson.JSON;

/**
 * @author monetto
 */
public class QuestionSectionDO {
    private Integer qid;
    private Integer sid;
    private String selectIntro;
    private String selectOption;
    private String sectionJson;

    public String getSectionJson() {
        return sectionJson;
    }

    public void setSectionJson(String sectionJson) {
        this.sectionJson = sectionJson;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getSelectIntro() {
        return selectIntro;
    }

    public void setSelectIntro(String selectIntro) {
        this.selectIntro = selectIntro;
    }

    public String getSelectOption() {
        return selectOption;
    }

    public void setSelectOption(String selectOption) {
        this.selectOption = selectOption;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
