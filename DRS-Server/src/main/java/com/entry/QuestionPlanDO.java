package com.entry;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class QuestionPlanDO {

    private Integer pid;
    private String description;
    private Integer done;
    private String planName;
    private List<Integer> qid;
    private String qidJson;

    public String getQidJson() {
        return qidJson;
    }

    public void setQidJson(String qidJson) {
        this.qidJson = qidJson;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public List<Integer> getQid() {
        return qid;
    }

    public void setQid(List<Integer> qid) {
        this.qid = qid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
