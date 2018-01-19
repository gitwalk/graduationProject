package com.chinesechess.pojo;


import com.chinesechess.util.Page;

public class AdminInform {

  private long id=-1;
  private String name=null;
  private String password=null;
  private String realName=null;
  private long role=-1;
  private String registerTime=null;
  private long isDeleted=-1;
  private String starTime = null;
  private String endTime = null;
  private Page page = null;

  @Override
  public String toString() {
    return "AdminInform{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            ", realName='" + realName + '\'' +
            ", role=" + role +
            ", registerTime='" + registerTime + '\'' +
            ", isDeleted=" + isDeleted +
            ", starTime='" + starTime + '\'' +
            ", endTime='" + endTime + '\'' +
            ", page=" + page +
            '}';
  }

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

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public long getRole() {
    return role;
  }

  public void setRole(long role) {
    this.role = role;
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
}
