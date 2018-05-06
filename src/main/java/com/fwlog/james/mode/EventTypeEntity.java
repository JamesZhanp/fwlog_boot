package com.fwlog.james.mode;

public class EventTypeEntity {
    String  attackType;
    Integer times;


    public EventTypeEntity(String attackType,Integer times){
        this.attackType = attackType;
        this.times = times;
    }
    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
