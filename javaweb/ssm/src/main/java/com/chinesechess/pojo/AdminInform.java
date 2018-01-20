package com.chinesechess.pojo;


import com.chinesechess.util.Page;

/**
 * 管理员信息实体类
 */
public class AdminInform {

  //管理员在数据库中的id
  private long id=-1;
  //管理员登录的账号
  private String name=null;
  //管理员登录的密码
  private String password=null;
  //管理员的真实姓名
  private String realName=null;
  //管理员的权限
  private long role=-1;
  //管理员账号注册时间
  private String registerTime=null;
  //管理员账号是否禁用
  private long isDeleted=-1;
  //搜索中的开始时间
  private String starTime = null;
  //搜索中的结束时间
  private String endTime = null;
  //页面的分页信息
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
