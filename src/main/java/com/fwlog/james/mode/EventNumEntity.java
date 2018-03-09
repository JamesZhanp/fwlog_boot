package com.fwlog.james.mode;

/**
 * 统计各个种类的访问事件的次数
 * created by jamesZhan on 2018/01/30
 */

public class EventNumEntity {
    private String attackName;
    private Integer attackNum;

    public String getAttackName() {
        return attackName;
    }

    public void setAttackName(String attackName) {
        this.attackName = attackName;
    }

    public Integer getAttackNum() {
        return attackNum;
    }

    public void setAttackNum(Integer attackNum) {
        this.attackNum = attackNum;
    }

    @Override
    public String toString() {
        return "EventNumEntity{" +
                "attackName='" + attackName + '\'' +
                ", attackNum=" + attackNum +
                '}';
    }
}
