package com.fwlog.james.entity;

import javax.persistence.*;

@Entity
@Table(name = "srcip")
public class Srcip {
  private Long id;
  private java.sql.Timestamp stime;
  private java.sql.Timestamp etime;
  private Long value;
  private Long number;
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

  public java.sql.Timestamp getStime() {
    return stime;
  }

  public void setStime(java.sql.Timestamp stime) {
    this.stime = stime;
  }

  public java.sql.Timestamp getEtime() {
    return etime;
  }

  public void setEtime(java.sql.Timestamp etime) {
    this.etime = etime;
  }

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }

  public Long getNumber() {
    return number;
  }

  public void setNumber(Long number) {
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
}
