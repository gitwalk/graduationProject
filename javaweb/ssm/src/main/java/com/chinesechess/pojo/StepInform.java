package com.chinesechess.pojo;

/**
 * 对局中每一步的实体类
 */
public class StepInform {

  //数据库id
  private Integer id=null;

  private Integer gameId=null;
  private Integer stepNum=null;
  private Integer chessNum=null;
  private Integer pointX=null;
  private Integer pointY=null;

  public StepInform(){

  }
  public StepInform(Integer chessNum, Integer pointX, Integer pointY) {
    this.chessNum = chessNum;
    this.pointX = pointX;
    this.pointY = pointY;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getGameId() {
    return gameId;
  }

  public void setGameId(Integer gameId) {
    this.gameId = gameId;
  }

  public Integer getStepNum() {
    return stepNum;
  }

  public void setStepNum(Integer stepNum) {
    this.stepNum = stepNum;
  }

  public Integer getChessNum() {
    return chessNum;
  }

  public void setChessNum(Integer chessNum) {
    this.chessNum = chessNum;
  }

  public Integer getPointX() {
    return pointX;
  }

  public void setPointX(Integer pointX) {
    this.pointX = pointX;
  }

  public Integer getPointY() {
    return pointY;
  }

  public void setPointY(Integer pointY) {
    this.pointY = pointY;
  }
}
