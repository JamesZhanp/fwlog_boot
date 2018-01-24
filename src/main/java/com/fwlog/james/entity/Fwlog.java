package com.fwlog.james.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fwlog")
public class Fwlog {
  private Long fwlogid;
  private Date time;
  private Long srcip;
  private Integer srcport;
  private Long destip;
  private Integer destport;
  private Integer number;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getFwlogid() {
    return fwlogid;
  }

  public void setFwlogid(Long fwlogid) {
    this.fwlogid = fwlogid;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public Long getSrcip() {
    return srcip;
  }

  public void setSrcip(Long srcip) {
    this.srcip = srcip;
  }

  public Integer getSrcport() {
    return srcport;
  }

  public void setSrcport(Integer srcport) {
    this.srcport = srcport;
  }

  public Long getDestip() {
    return destip;
  }

  public void setDestip(Long destip) {
    this.destip = destip;
  }

  public Integer getDestport() {
    return destport;
  }

  public void setDestport(Integer destport) {
    this.destport = destport;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return "Fwlog{" +
            "fwlogid=" + fwlogid +
            ", time=" + time +
            ", srcip=" + srcip +
            ", srcport=" + srcport +
            ", destip=" + destip +
            ", destport=" + destport +
            ", number=" + number +
            '}';
  }
}
