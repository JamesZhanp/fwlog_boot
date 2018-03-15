package com.fwlog.james.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
  private Integer id;
  private String name;
  private String password;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @NotBlank(message = "用户名不能为空")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @NotBlank(message = "密码不能为空")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
