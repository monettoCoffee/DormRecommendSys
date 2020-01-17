package com.entry;

import com.alibaba.fastjson.JSON;

/**
 * @author monetto
 */
public class QuestionExtraDO {
    private Integer eid;
    private Integer qid;
    private String extIntro;
    private String extPlaceholder;
    private String extDefaultValue;
    private String extAddButtonValue;

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getExtAddButtonValue() {
        return extAddButtonValue;
    }

    public void setExtAddButtonValue(String extAddButtonValue) {
        this.extAddButtonValue = extAddButtonValue;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getExtIntro() {
        return extIntro;
    }

    public void setExtIntro(String extIntro) {
        this.extIntro = extIntro;
    }

    public String getExtPlaceholder() {
        return extPlaceholder;
    }

    public void setExtPlaceholder(String extPlaceholder) {
        this.extPlaceholder = extPlaceholder;
    }

    public String getExtDefaultValue() {
        return extDefaultValue;
    }

    public void setExtDefaultValue(String extDefaultValue) {
        this.extDefaultValue = extDefaultValue;
    }


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
