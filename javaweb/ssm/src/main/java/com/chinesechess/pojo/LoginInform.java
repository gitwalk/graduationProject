package com.chinesechess.pojo;

import java.awt.*;

/**
 * 表示每次登录的实体类
 */
public class LoginInform implements Comparable<LoginInform>{


  //数据库id
  private long id=-1;
  //登录用户的id
  private long userId=-1;
  //用户登录的ip地址
  private String loginIp=null;
  //用户登录的时间
  private java.sql.Timestamp loginTime=null;
  //用户的下线的时间
  private java.sql.Timestamp logoutTime=null;
  //用户此次在线的时间长
  private double time=-1;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public String getLoginIp() {
    return loginIp;
  }

  public void setLoginIp(String loginIp) {
    this.loginIp = loginIp;
  }


  public java.sql.Timestamp getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(java.sql.Timestamp loginTime) {
    this.loginTime = loginTime;
  }


  public java.sql.Timestamp getLogoutTime() {
    return logoutTime;
  }

  public void setLogoutTime(java.sql.Timestamp logoutTime) {
    this.logoutTime = logoutTime;
  }


  public double getTime() {
    return time;
  }

  public void setTime(double time) {
    this.time = time;
  }

  @Override
  public int compareTo(LoginInform o) {

    return o.getLoginTime().compareTo(this.getLoginTime());

  }


}
