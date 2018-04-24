package com.chinesechess.util;

/**
 * 用于表示当前的信息
 */
public class Chessman {
    //当前棋子是否闪烁
    private boolean isflash = false;
    //当前棋子是否存在
    private boolean isexit=true;
    //当前棋子的逻辑坐标1-32
    private Integer x=-1;
    //当前棋子的逻辑坐标1-32
    private Integer y=-1;
    //当前棋子的实际坐标
    private Integer positionX = -1;
    //当前棋子的实际坐标
    private Integer positionY = -1;
    //当前的棋子号
    private Integer num=-1;
    //当前棋子字的图片地址
    private String bgurl=null;

    public Chessman(){

    }

    public Chessman(Integer x, Integer y,String bgurl,int num) {
        this.isflash = false;
        this.isexit=true;
        this.positionX = 4+x*66;
        this.positionY = 4+y*68;
        this.num=num;
        this.bgurl=bgurl;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Chessman{" +
                "isflash=" + isflash +
                ", isexit=" + isexit +
                ", x=" + x +
                ", y=" + y +
                ", positionX=" + positionX +
                ", positionY=" + positionY +
                ", num=" + num +
                ", bgurl='" + bgurl + '\'' +
                '}';
    }

    public boolean isIsflash() {
        return isflash;
    }

    public void setIsflash(boolean isflash) {
        this.isflash = isflash;
    }

    public boolean isIsexit() {
        return isexit;
    }

    public void setIsexit(boolean isexit) {
        this.isexit = isexit;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBgurl() {
        return bgurl;
    }

    public void setBgurl(String bgurl) {
        this.bgurl = bgurl;
    }
}
