package com.chinesechess.util;

import com.chinesechess.util.RoomInform;

/**
 * 用户每一步提交的棋子信息
 */
public class SubmitStep {
    Integer x=-1;
    Integer y=-1;
    Integer i=-1;
    RoomInform roomInform=null;

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
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

    public RoomInform getRoomInform() {
        return roomInform;
    }

    public void setRoomInform(RoomInform roomInform) {
        this.roomInform = roomInform;
    }
}
