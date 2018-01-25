package com.fwlog.james.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "destip")
public class Destip {
  private Long id;
  private Date stime;
  private Date etime;
  private Long value;
  private Integer number;
  private Double average;
  private Double variance;

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

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Double getAverage() {
    return average;
  }

  public void setAverage(Double average) {
    this.average = average;
  }

  public Double getVariance() {
    return variance;
  }

  public void setVariance(Double variance) {
    this.variance = variance;
  }

  @Override
  public String toString() {
    return "Destip{" +
            "id=" + id +
            ", stime=" + stime +
            ", etime=" + etime +
            ", value=" + value +
            ", number=" + number +
            ", average=" + average +
            ", variance=" + variance +
            '}';
  }
}
