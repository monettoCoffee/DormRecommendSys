package com.entry;

import com.alibaba.fastjson.JSON;

/**
 * @author monetto
 */
public class UserChosenDO {
    private Integer cid;
    private Integer uid;
    private String chosen;
    private Integer pid;


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public UserChosenDO(String chosen) {
        this.chosen = chosen;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getChosen() {
        return chosen;
    }

    public void setChosen(String chosen) {
        this.chosen = chosen;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
