package com.fwlog.james.entity;

import javax.persistence.*;

@Entity
@Table(name = "sensitiveport")

public class Sensitiveport {
  private Integer id;
  private Integer port;
  private String desc;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
