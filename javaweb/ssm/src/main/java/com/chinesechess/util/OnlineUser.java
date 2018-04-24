package com.chinesechess.util;

import com.chinesechess.pojo.LoginInform;
import com.chinesechess.pojo.UserInform;

/**
 * 用于记录当前在线的用户
 */
public class OnlineUser {

    //用于记录用户的信息
    private UserInform userInform;
    //用于记录用户此次登录的信息
    private LoginInform loginInform;
    //用于记录用户当前在线的状态：0表示掉线（在对局中），
    // 1表示红方等待，2表示黑方等待，3表示观战，4表示在游戏大厅
    // 5表示红方进行,6表示黑方进行
    private Integer userState=4;
    //用于记录在线用户所在的房间号，如果在大厅则为-1
    private Integer roomId=-1;

    @Override
    public String toString() {
        return "OnlineUser{" +
                "userInform=" + userInform +
                ", loginInform=" + loginInform +
                ", userState=" + userState +
                ", roomId=" + roomId +
                '}';
    }

    public UserInform getUserInform() {
        return userInform;
    }

    public void setUserInform(UserInform userInform) {
        this.userInform = userInform;
    }

    public LoginInform getLoginInform() {
        return loginInform;
    }

    public void setLoginInform(LoginInform loginInform) {
        this.loginInform = loginInform;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
