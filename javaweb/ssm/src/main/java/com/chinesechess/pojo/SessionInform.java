package com.chinesechess.pojo;


public class SessionInform {


  private long id=-1;
  private long gameId=-1;
  private java.sql.Timestamp sendTime=null;
  private long sendUserId=-1;
  private long receiveUserId=-1;
  private String context=null;


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


  public java.sql.Timestamp getSendTime() {
    return sendTime;
  }

  public void setSendTime(java.sql.Timestamp sendTime) {
    this.sendTime = sendTime;
  }


  public long getSendUserId() {
    return sendUserId;
  }

  public void setSendUserId(long sendUserId) {
    this.sendUserId = sendUserId;
  }


  public long getReceiveUserId() {
    return receiveUserId;
  }

  public void setReceiveUserId(long receiveUserId) {
    this.receiveUserId = receiveUserId;
  }


  public String getContext() {
    return context;
  }

  public void setContext(String context) {
    this.context = context;
  }

}
