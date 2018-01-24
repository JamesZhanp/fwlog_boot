package com.fwlog.james.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="rawfwlog")
public class Rawfwlog {

  private Long rawfwlogid;
  private String internalip;
  private Date producetime;
  private Date savetime;
  private String mathedstrategy;
  private String accessstrategy;
  private Long originalsrcip;
  private Integer originalsrcport;
  private Long originaldestip;
  private Integer originaldestport;
  private Long convertedsrcip;
  private Integer convertedsrcport;
  private Long converteddestip;
  private Integer converteddestport;
  private int protocolnumber;
  private Integer safetydomain;
  private Integer action;

  @javax.persistence.Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  public Long getRawfwlogid() {
    return rawfwlogid;
  }

  public void setRawfwlogid(Long rawfwlogid) {
    this.rawfwlogid = rawfwlogid;
  }

  public String getInternalip() {
    return internalip;
  }

  public void setInternalip(String internalip) {
    this.internalip = internalip;
  }

  public Date getProducetime() {
    return producetime;
  }

  public void setProducetime(Date producetime) {
    this.producetime = producetime;
  }

  public Date getSavetime() {
    return savetime;
  }

  public void setSavetime(Date savetime) {
    this.savetime = savetime;
  }

  public String getMathedstrategy() {
    return mathedstrategy;
  }

  public void setMathedstrategy(String mathedstrategy) {
    this.mathedstrategy = mathedstrategy;
  }

  public String getAccessstrategy() {
    return accessstrategy;
  }

  public void setAccessstrategy(String accessstrategy) {
    this.accessstrategy = accessstrategy;
  }

  public Long getOriginalsrcip() {
    return originalsrcip;
  }

  public void setOriginalsrcip(Long originalsrcip) {
    this.originalsrcip = originalsrcip;
  }

  public Integer getOriginalsrcport() {
    return originalsrcport;
  }

  public void setOriginalsrcport(Integer originalsrcport) {
    this.originalsrcport = originalsrcport;
  }

  public Long getOriginaldestip() {
    return originaldestip;
  }

  public void setOriginaldestip(Long originaldestip) {
    this.originaldestip = originaldestip;
  }

  public Integer getOriginaldestport() {
    return originaldestport;
  }

  public void setOriginaldestport(Integer originaldestport) {
    this.originaldestport = originaldestport;
  }

  public Long getConvertedsrcip() {
    return convertedsrcip;
  }

  public void setConvertedsrcip(Long convertedsrcip) {
    this.convertedsrcip = convertedsrcip;
  }

  public Integer getConvertedsrcport() {
    return convertedsrcport;
  }

  public void setConvertedsrcport(Integer convertedsrcport) {
    this.convertedsrcport = convertedsrcport;
  }

  public Long getConverteddestip() {
    return converteddestip;
  }

  public void setConverteddestip(Long converteddestip) {
    this.converteddestip = converteddestip;
  }

  public Integer getConverteddestport() {
    return converteddestport;
  }

  public void setConverteddestport(Integer converteddestport) {
    this.converteddestport = converteddestport;
  }

  public int getProtocolnumber() {
    return protocolnumber;
  }

  public void setProtocolnumber(int protocolnumber) {
    this.protocolnumber = protocolnumber;
  }

  public Integer getSafetydomain() {
    return safetydomain;
  }

  public void setSafetydomain(Integer safetydomain) {
    this.safetydomain = safetydomain;
  }

  public Integer getAction() {
    return action;
  }

  public void setAction(Integer action) {
    this.action = action;
  }

  @Override
  public String toString(){
    return "rawFwlog{" +
            "id=" + rawfwlogid +
            ", internalIP='" + internalip + '\'' +
            ", produceTime=" + producetime +
            ", safeTime=" + savetime +
            ", mathedStrategy='" + mathedstrategy + '\'' +
            ",accessStrategy='" + accessstrategy + '\'' +
            ", orignalSrcIp='" + originalsrcip + '\'' +
            ", orignalSrcPort='" + originalsrcport + '\'' +
            ", orignalDestIp='" + originaldestip + '\'' +
            ", orignalDestPort='" + originaldestport + '\'' +
            ", convertedSrcIp='" + convertedsrcip + '\'' +
            ", convertedSrcPort='" + convertedsrcport + '\'' +
            ", convertedDestIp='" + converteddestip + '\'' +
            ", convertedDestPort='" + converteddestport + '\'' +
            ", protocolNumber='" + protocolnumber + '\'' +
            ", safetyDomain='" + safetydomain + '\'' +
            ", action='" + action + '\'' +
            "}";
  }
}
