package com.fwlog.james.analysis.statisticsAnalysis;

import java.util.Date;

public class IP {
    private Date sTime;
    private Date eTime;
    private Long value;
    private Integer count;

    public Date getsTime() {
        return sTime;
    }

    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }

    public Date geteTime() {
        return eTime;
    }

    public void seteTime(Date eTime) {
        this.eTime = eTime;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "IP{" +
                "sTime=" + sTime +
                ", eTime=" + eTime +
                ", value=" + value +
                ", count=" + count +
                '}';
    }
}
