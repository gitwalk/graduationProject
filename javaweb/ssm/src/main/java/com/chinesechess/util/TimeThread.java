package com.chinesechess.util;

import com.alibaba.fastjson.JSONObject;
import com.chinesechess.util.OnlineUser;
import com.chinesechess.util.RoomInform;
import com.chinesechess.util.TimeInfom;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TimeThread extends Thread {
    //记录时间的线程
    private Thread thread=null;

    private Integer roomId=null;

    private Map<Integer, WebSocketSession> users = null;

    //当前的房间列表
    private TreeMap<Integer,RoomInform> rooms=null ;

    public TimeThread(){

    }

    public TimeThread(Integer roomId,TreeMap<Integer,RoomInform> rooms, Map<Integer, WebSocketSession> users) {
        this.thread = new Thread();
        this.rooms = rooms;
        this.users = users;
        this.roomId=roomId;
    }


    @Override
    public void run() {
        while (true){
            RoomInform roomInform=rooms.get(roomId);
            try {
                if(roomInform==null||roomInform.getRoomState()==0||roomInform.getRoomState()==2) break;
                this.thread.sleep(1000);
                Integer redSteptime=roomInform.getRedSteptime();
                Integer redTime=roomInform.getRedTime();
                Integer blackSteptime=roomInform.getBlackSteptime();
                Integer blackTime=roomInform.getBlackTime();
                if(roomInform.isRed()){
                    redSteptime--;
                    redTime--;

                    if(redSteptime<0||redTime<0) break;
                }
                else {
                    blackSteptime--;
                    blackTime--;
                    if(blackSteptime<0||blackTime<0) break;
                }
                roomInform.setRedSteptime(redSteptime);
                roomInform.setRedTime(redTime);
                roomInform.setBlackSteptime(blackSteptime);
                roomInform.setBlackTime(blackTime);
                TimeInfom timeInfom=new TimeInfom(redSteptime, blackSteptime,blackTime, redTime);
                String str=JSONObject.toJSON(timeInfom).toString();
                str+="|steptime";
                System.out.println(str);
                this.sendMessageToRoomAllUsers(new TextMessage(str));
                Integer num=-1;
                if(blackTime==0){
                    num=4;
                }
                else if(blackSteptime==0){
                    num=2;

                }
                else if(redSteptime==0){
                    num=3;

                }
                else if(redTime==0){
                    num=5;

                }
                if(num!=-1){
                    String str1=JSONObject.toJSON(num).toString();
                    str1+="|overtime";
                    //设置当前房间状态为结束
                    rooms.get(roomId).setRoomState(2);
                    this.sendMessageToRoomAllUsers(new TextMessage(str1));

                    String str2=JSONObject.toJSON(rooms).toString();
                    str2+="|joingameLobby";
                    this.sendMessageToAllUsers(new TextMessage(str2));

                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<Integer> clientIds = users.keySet();
        WebSocketSession session = null;
        for (Integer clientId : clientIds) {
            try {

                session = users.get(clientId);
                if (session!=null&&session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return  allSendSuccess;
    }


    /**
     * 广播信息给指定房间的人
     * @param message
     * @return
     */
    public boolean sendMessageToRoomAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        TreeMap<Integer,OnlineUser> onlineUserTreeMap=rooms.get(roomId).getOnlineUserList();
        Set<Integer> clientIds = onlineUserTreeMap.keySet();
        WebSocketSession session = null;
        for (Integer clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return  allSendSuccess;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public TreeMap<Integer, RoomInform> getRooms() {
        return rooms;
    }

    public void setRooms(TreeMap<Integer, RoomInform> rooms) {
        this.rooms = rooms;
    }

    public  Map<Integer, WebSocketSession> getUsers() {
        return users;
    }


}
