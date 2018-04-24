package com.chinesechess.util;

public class TimeInfom {
    Integer redSteptime;
    Integer blackSteptime;
    Integer blackTime;
    Integer redTime;

    public TimeInfom() {
    }

    public TimeInfom(Integer redSteptime, Integer blackSteptime, Integer blackTime, Integer redTime) {
        this.redSteptime = redSteptime;
        this.blackSteptime = blackSteptime;
        this.blackTime = blackTime;
        this.redTime = redTime;
    }

    public Integer getRedSteptime() {
        return redSteptime;
    }

    public void setRedSteptime(Integer redSteptime) {
        this.redSteptime = redSteptime;
    }

    public Integer getBlackSteptime() {
        return blackSteptime;
    }

    public void setBlackSteptime(Integer blackSteptime) {
        this.blackSteptime = blackSteptime;
    }

    public Integer getBlackTime() {
        return blackTime;
    }

    public void setBlackTime(Integer blackTime) {
        this.blackTime = blackTime;
    }

    public Integer getRedTime() {
        return redTime;
    }

    public void setRedTime(Integer redTime) {
        this.redTime = redTime;
    }
}
