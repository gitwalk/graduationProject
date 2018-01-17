package com.chinesechess.pojo;


public class UserInform {

  private long id=-1;
  private String name=null;
  private String password=null;
  private String emailAddress=null;
  private long role=-1;
  private long sex=-1;
  private java.sql.Timestamp registerTime=null;
  private long isDeleted=-1;



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


  public java.sql.Timestamp getRegisterTime() {
    return registerTime;
  }

  public void setRegisterTime(java.sql.Timestamp registerTime) {
    this.registerTime = registerTime;
  }


  public long getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(long isDeleted) {
    this.isDeleted = isDeleted;
  }

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
