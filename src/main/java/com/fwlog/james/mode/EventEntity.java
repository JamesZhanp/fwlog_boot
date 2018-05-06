package com.fwlog.james.mode;

import java.util.Date;

/**
 * 与前端事件相关的实体类
 * 确认的攻击事件
 * created by jamesZhan on 2018/01/30
 */
public class EventEntity {
    private Date starTime;
    private Date endTime;
    private String ipAddress;
    private String desc;//本次攻击事件的详细介绍
    private String attackType;//攻击类型
    private String advice;//建议

    public EventEntity(){}
    public EventEntity( Date starTime, Date endTime, String ipAddress, String desc, String attackType, String advice) {
        this.starTime = starTime;
        this.endTime = endTime;
        this.ipAddress = ipAddress;
        this.desc = desc;
        this.attackType = attackType;
        this.advice = advice;
    }

    public Date getStarTime() {
        return starTime;
    }

    public void setStarTime(Date starTime) {
        this.starTime = starTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @Override
    public String toString() {
        return "EventEntity{" +
                ", starTime=" + starTime +
                ", endTime=" + endTime +
                ", ipAddress='" + ipAddress + '\'' +
                ", desc='" + desc + '\'' +
                ", attackType='" + attackType + '\'' +
                ", advice='" + advice + '\'' +
                '}';
    }
}
