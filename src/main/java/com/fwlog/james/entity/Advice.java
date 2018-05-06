package com.fwlog.james.entity;

import javax.persistence.*;

/**
 * 意见对应的class
 */
@Entity
@Table(name = "advice")
public class Advice {

    private Integer id;
    private String type;//攻击的类型
    private String advice;//对于此类攻击的意见

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
