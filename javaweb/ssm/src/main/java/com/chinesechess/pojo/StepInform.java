package com.chinesechess.pojo;

/**
 * 对局中每一步的实体类
 */
public class StepInform {

  //数据库id
  private long id=-1;

  private long gameId=-1;
  private long stepNum=-1;
  private long chessNum=-1;
  private long pointX=-1;
  private long pointY=-1;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getGameId() {
    return gameId;
  }

  public void setGameId(long gameId) {
    this.gameId = gameId;
  }


  public long getStepNum() {
    return stepNum;
  }

  public void setStepNum(long stepNum) {
    this.stepNum = stepNum;
  }


  public long getChessNum() {
    return chessNum;
  }

  public void setChessNum(long chessNum) {
    this.chessNum = chessNum;
  }


  public long getPointX() {
    return pointX;
  }

  public void setPointX(long pointX) {
    this.pointX = pointX;
  }


  public long getPointY() {
    return pointY;
  }

  public void setPointY(long pointY) {
    this.pointY = pointY;
  }

}
