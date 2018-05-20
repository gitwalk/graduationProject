package com.chinesechess.pojo;


import com.chinesechess.util.Page;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class UserInform {

  //数据库的id
  private long id=-1;
  //用户账号
  private String name=null;
  //用户密码
  private String password=null;
  //用户电子邮件
  private String emailAddress=null;
  //用户权限
  private long role=-1;
  //用户性别
  private long sex=-1;
  //用户注册时间
  private String registerTime=null;
  //用户是否禁用
  private long isDeleted=-1;
  //搜索时的开始和结束时间
  private String starTime=null;
  private String endTime=null;
  //页面的分页信息
  private Page page=null;
  //登录次数
  private long loginNum=-1;
  //对局次数
  private long gameNum=-1;
  //在线总时长(单位毫秒)
  private long allLoginTime=-1;
  //用户胜率(0-1之间)
  private double winningRate=-1;
  //每次登录的信息
  private List<LoginInform> loginInformList=null;


  public long getAllLoginTime() { return allLoginTime; }

  public void setAllLoginTime(long allLoginTime) { this.allLoginTime = allLoginTime; }

  public double getWinningRate() { return winningRate; }

  public void setWinningRate(double winningRate) { this.winningRate = winningRate; }

  public List<LoginInform> getLoginInformList() { return loginInformList; }

  public void setLoginInformList(List<LoginInform> loginInformList) { this.loginInformList = loginInformList; }

  public long getLoginNum() { return loginNum; }

  public void setLoginNum(long loginNum) { this.loginNum = loginNum; }

  public long getGameNum() { return gameNum; }

  public void setGameNum(long gameNum) { this.gameNum = gameNum; }

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


  @Override
  public String toString() {
    return "UserInform{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            ", emailAddress='" + emailAddress + '\'' +
            ", role=" + role +
            ", sex=" + sex +
            ", registerTime='" + registerTime + '\'' +
            ", isDeleted=" + isDeleted +
            ", starTime='" + starTime + '\'' +
            ", endTime='" + endTime + '\'' +
            ", page=" + page +
            ", loginNum=" + loginNum +
            ", gameNum=" + gameNum +
            ", allLoginTime=" + allLoginTime +
            ", winningRate=" + winningRate +
            ", loginInformList=" + loginInformList +
            '}';
  }
}
