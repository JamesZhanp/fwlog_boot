package com.fwlog.james.entity;

import javax.persistence.*;

@Entity
@Table(name = "events")

public class Events {
  private Integer id;
  private String description;
  private String attackType;

  @Id
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAttackType() {
    return attackType;
  }

  public void setAttackType(String attackType) {
    this.attackType = attackType;
  }
}
