package com.chinesechess.pojo;


import java.util.List;

/**
 * 对局游戏信息实体类
 */
public class GameInform {

  //表示游戏对局的id
  private long id=-1;
  //红方用户的id
  private long redUserId=-1;
  //黑方用户的id
  private long blackUserId=-1;
  //游戏开始的时间
  private java.sql.Timestamp startTime=null;
  //游戏结束的时间
  private java.sql.Timestamp endTime=null;
  //用户设置的局时
  private long setTime=-1;
  //用户设置的步时
  private long setStepTime=-1;
  //游戏输赢的状态
  private long gameState=-1;
  //对局的步数
  private long stepNum=-1;
  //对局中每一步的信息(不在数据库中)
  private List<StepInform> stepInformList=null;
  //记录红方信息(不在数据库中)
  private UserInform redUserInform=null;
  //记录黑方信息（不在数据库中）
  private UserInform blackUserInform=null;


  public UserInform getRedUserInform() {
    return redUserInform;
  }

  public void setRedUserInform(UserInform redUserInform) {
    this.redUserInform = redUserInform;
  }

  public UserInform getBlackUserInform() {
    return blackUserInform;
  }

  public void setBlackUserInform(UserInform blackUserInform) {
    this.blackUserInform = blackUserInform;
  }

  public List<StepInform> getStepInformList() {
    return stepInformList;
  }

  public void setStepInformList(List<StepInform> stepInformList) {
    this.stepInformList = stepInformList;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getRedUserId() {
    return redUserId;
  }

  public void setRedUserId(long redUserId) {
    this.redUserId = redUserId;
  }


  public long getBlackUserId() {
    return blackUserId;
  }

  public void setBlackUserId(long blackUserId) {
    this.blackUserId = blackUserId;
  }


  public java.sql.Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(java.sql.Timestamp startTime) {
    this.startTime = startTime;
  }


  public java.sql.Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(java.sql.Timestamp endTime) {
    this.endTime = endTime;
  }


  public long getSetTime() {
    return setTime;
  }

  public void setSetTime(long setTime) {
    this.setTime = setTime;
  }


  public long getSetStepTime() {
    return setStepTime;
  }

  public void setSetStepTime(long setStepTime) {
    this.setStepTime = setStepTime;
  }


  public long getGameState() {
    return gameState;
  }

  public void setGameState(long gameState) {
    this.gameState = gameState;
  }


  public long getStepNum() {
    return stepNum;
  }

  public void setStepNum(long stepNum) {
    this.stepNum = stepNum;
  }

}
