package com.fwlog.james.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "suspectedevents")
public class Suspectedevents {
  private Long id;
  private Date stime;
  private Date etime;
  private Integer type;
  private Long value;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getStime() {
    return stime;
  }

  public void setStime(Date stime) {
    this.stime = stime;
  }

  public Date getEtime() {
    return etime;
  }

  public void setEtime(Date etime) {
    this.etime = etime;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }
}
