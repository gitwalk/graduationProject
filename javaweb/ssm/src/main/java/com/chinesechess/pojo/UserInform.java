package com.chinesechess.pojo;


import com.chinesechess.util.Page;

import java.sql.Timestamp;
import java.util.Date;

public class UserInform {

  private long id=-1;
  private String name=null;
  private String password=null;
  private String emailAddress=null;
  private long role=-1;
  private long sex=-1;
  private String registerTime=null;
  private long isDeleted=-1;
  private String starTime=null;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public long getRole() {
    return role;
  }

  public void setRole(long role) {
    this.role = role;
  }

  public long getSex() {
    return sex;
  }

  public void setSex(long sex) {
    this.sex = sex;
  }

  public String getRegisterTime() {
    return registerTime;
  }

  public void setRegisterTime(String registerTime) {
    this.registerTime = registerTime;
  }

  public long getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(long isDeleted) {
    this.isDeleted = isDeleted;
  }

  public String getStarTime() {
    return starTime;
  }

  public void setStarTime(String starTime) {
    this.starTime = starTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  private String endTime=null;
  private Page page=null;

  @Override
  public String toString() {
    return "UserInform{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            ", emailAddress='" + emailAddress + '\'' +
            ", role=" + role +
            ", sex=" + sex +
            ", registerTime=" + registerTime +
            ", isDeleted=" + isDeleted +
            '}';
  }
}
