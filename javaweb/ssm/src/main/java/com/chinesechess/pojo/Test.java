package com.chinesechess.pojo;


public class Test {

  private long id;
  private String name;


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

    @Override
    public String toString() {
        return "Test [id:"+this.getId()+",name:"+this.getName()+"]";
    }
}
