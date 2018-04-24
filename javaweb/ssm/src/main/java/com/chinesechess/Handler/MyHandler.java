package com.chinesechess.Handler;


import com.alibaba.fastjson.JSONObject;
import com.chinesechess.pojo.*;
import com.chinesechess.service.GameInformService;
import com.chinesechess.service.LoginInformService;
import com.chinesechess.service.StepInformService;
import com.chinesechess.util.OnlineUser;
import com.chinesechess.util.RoomInform;
import com.chinesechess.util.SubmitStep;
import com.chinesechess.util.TimeThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MyHandler extends TextWebSocketHandler {
    //用户的连接
    private static final Map<Integer, WebSocketSession> users;
    //掉线用户的连接
    private static final Map<Integer, WebSocketSession> outusers;
    //当前的房间列表
    private static final TreeMap<Integer,RoomInform> rooms ;
    //用户的连接列表
    private static final TreeMap<Integer,OnlineUser> onlineUsers;
    //掉线用户的连接列表
    private static final TreeMap<Integer,OnlineUser> outonlineUsers;
    //突然消失的房间列表
    private static final TreeMap<Integer,RoomInform> outrooms ;
    //用户标识
    private static final String CLIENT_ID = "userId";


    private static GameInformService gameInformService;

    private static StepInformService stepInformService;

    private static LoginInformService loginInformService;

    @Autowired
    public void setLoginInformService(LoginInformService loginInformService){ this.loginInformService=loginInformService; }

    @Autowired
    public void setGameInformService(GameInformService gameInformService){
        this.gameInformService=gameInformService;
    }

    @Autowired
    public void setStepInformService(StepInformService stepInformService){
        this.stepInformService=stepInformService;
    }

    static {
        users = new HashMap<>();
        outusers= new HashMap<>();
        rooms=new TreeMap<Integer,RoomInform>();
        outrooms=new TreeMap<Integer,RoomInform>();
        onlineUsers=new TreeMap<Integer,OnlineUser>();
        outonlineUsers=new TreeMap<Integer,OnlineUser>();
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("成功建立连接");
        Integer userId = getClientId(session);
        System.out.println(userId);
        if (userId != null) {
            if(!users.containsValue(userId)) {
                UserInform olduserInform=(UserInform) session.getAttributes().get("olduserInform");
                if(olduserInform!=null){
                    Integer oldUserid=(int)olduserInform.getId();
                    removeUser(oldUserid);
               }
                users.put(userId, session);
            }
            UserInform userInform=(UserInform) session.getAttributes().get("userInform");
           String str= JSONObject.toJSON(userInform).toString();
            //GameInformService temp=gameInformService;
            session.sendMessage(new TextMessage(str));
            System.out.println("用户的ID："+userId);
            System.out.println("用户的seeion："+session);
        }
    }

    @Override
    public synchronized  void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ...
        System.out.println("------------:"+message.getPayload());

        WebSocketMessage message1 = new TextMessage("server:"+message);
        try {
            session.sendMessage(message1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  处理当前和局的结果(拒绝或者同意)
     */
    public void dealpace(Integer roomId,Integer paceNum,Integer resultNum){
        //如果同意，则向房间内的人广播
        if(resultNum==1){
            String str1=JSONObject.toJSON(1).toString();
            str1+="|paceresult";
            //设置当前房间状态为结束
            rooms.get(roomId).setRoomState(2);
            this.sendMessageToRoomAllUsers(roomId,new TextMessage(str1));
            this.gameInformresuiltDao(roomId,8);
        }
        //如果拒绝，则向请求方，发消息
        else {
            if(paceNum==8){
                String str1=JSONObject.toJSON(0).toString();
                str1+="|paceresult";
                TreeMap<Integer,OnlineUser> list=rooms.get(roomId).getOnlineUserList();
                for (Map.Entry<Integer, OnlineUser> entry : list.entrySet()) {

                    Integer num=entry.getValue().getUserState();
                    if(num==6){
                        this.sendMessageToUser(entry.getKey(),new TextMessage(str1));
                        break;
                    }
                }

            }
            else if(paceNum==9){
                String str1=JSONObject.toJSON(0).toString();
                str1+="|paceresult";
                TreeMap<Integer,OnlineUser> list=rooms.get(roomId).getOnlineUserList();
                for (Map.Entry<Integer, OnlineUser> entry : list.entrySet()) {

                    Integer num=entry.getValue().getUserState();
                    if(num==5){
                        this.sendMessageToUser(entry.getKey(),new TextMessage(str1));
                        break;
                    }
                }
            }
        }

    }

    /**
     * 处理游戏结果，如果是请求和局，则另外处理
     * @param roomId
     * @param resultNum
     */
    public void gameResult(Integer roomId,Integer resultNum){
        //红绝杀黑
        if(resultNum==0){
            String str1=JSONObject.toJSON(0).toString();
            str1+="|kill";
            //设置当前房间状态为结束
            rooms.get(roomId).setRoomState(2);
            this.sendMessageToRoomAllUsers(roomId,new TextMessage(str1));
        }
        //黑绝杀红
        else if(resultNum==1){
            String str1=JSONObject.toJSON(1).toString();
            str1+="|kill";
            //设置当前房间状态为结束
            rooms.get(roomId).setRoomState(2);
            this.sendMessageToRoomAllUsers(roomId,new TextMessage(str1));
        }
        //黑方投降
        else if(resultNum==6){
            String str1=JSONObject.toJSON(6).toString();
            str1+="|surrender";
            //设置当前房间状态为结束
            rooms.get(roomId).setRoomState(2);
            this.sendMessageToRoomAllUsers(roomId,new TextMessage(str1));

        }
        //红方投降
        else if(resultNum==7){
            String str1=JSONObject.toJSON(7).toString();
            str1+="|surrender";
            //设置当前房间状态为结束
            rooms.get(roomId).setRoomState(2);
            this.sendMessageToRoomAllUsers(roomId,new TextMessage(str1));
        }
        //黑方求和
        else if(resultNum==8){
            String str1=JSONObject.toJSON(8).toString();
            str1+="|peace";

            TreeMap<Integer,OnlineUser> list=rooms.get(roomId).getOnlineUserList();

            for (Map.Entry<Integer, OnlineUser> entry : list.entrySet()) {

                Integer num=entry.getValue().getUserState();
                if(num==5){
                    this.sendMessageToUser(entry.getKey(),new TextMessage(str1));
                    break;
                }
            }

        }
        //红方求和
        else if(resultNum==9){
            String str1=JSONObject.toJSON(9).toString();
            str1+="|peace";

            TreeMap<Integer,OnlineUser> list=rooms.get(roomId).getOnlineUserList();

            for (Map.Entry<Integer, OnlineUser> entry : list.entrySet()) {

                Integer num=entry.getValue().getUserState();
                if(num==6){
                    this.sendMessageToUser(entry.getKey(),new TextMessage(str1));
                    break;
                }
            }
        }
        if(resultNum!=9&&resultNum!=8){
            this.gameInformresuiltDao(roomId,resultNum);

        }
    }

    /**
     * 将游戏结果写入数据库
     * @param roomId
     * @param resultNum
     */
    public void gameInformresuiltDao(Integer roomId,Integer resultNum){
        RoomInform roomInform=rooms.get(roomId);
        GameInform gameInform=roomInform.getGameInform();
        gameInform.setStepNum(roomInform.getStepInformList().size());
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(new Date());
        Timestamp timestamp = Timestamp.valueOf(dateString);
        gameInform.setEndTime(timestamp);
        gameInform.setGameState(resultNum);

        //写入数据库
        this.gameInformService.update(gameInform);
    }

    /**
     * 处理每一步提交上来的信息
     * @param submitStep
     */
    public void dealStepinform(SubmitStep submitStep){
        Integer x=submitStep.getX();
        Integer y=submitStep.getY();
        Integer chessNum=submitStep.getI();
        //设置当前一步信息
        StepInform stepInform=new StepInform(chessNum,x,y);

        RoomInform roomInform= submitStep.getRoomInform();
        Integer roomId=roomInform.getRoomId();
        RoomInform oldroomInform=rooms.get(roomId);

        stepInform.setGameId((int)oldroomInform.getGameInform().getId());
        stepInform.setStepNum(oldroomInform.getStepInformList().size()+1);

        //写入数据库
        stepInformService.insert(stepInform);

        //加入步骤
        oldroomInform.getStepInformList().add(stepInform);
        //将原来房间的信息填现在房间
        roomInform.setStepInformList(oldroomInform.getStepInformList());
        roomInform.setGameInform(oldroomInform.getGameInform());
        //覆盖原来的房间信息
        rooms.put(roomId,roomInform);

        if(chessNum % 2==0){
            rooms.get(roomId).setBlackSteptime(180);
            rooms.get(roomId).setRed(true);

        }
        else {
            rooms.get(roomId).setRedSteptime(180);
            rooms.get(roomId).setRed(false);
        }

        //向房间内的人广播这步棋
        String str=JSONObject.toJSON(stepInform).toString();
        str+="|submitStepinform";
        sendMessageToRoomAllUsers(roomId,new TextMessage(str));
    }

    /**
     * 向rooms列表中添加新建的房间
     * 要求该用户必须没有在进行中的对局（这一点在加入大厅时保证）
     */
    public String addRoom(UserInform userInform){

        //房间列表中最后一个房间号加1，是新房间的房间号
        Integer setRoomId;
        if(rooms.size()==1&&rooms.lastKey()==0){
            setRoomId=1;
        }
        else{
            setRoomId=rooms.lastKey()+1;
        }
        //当前请求建房的用户id
        Integer userId=(int)userInform.getId();

        RoomInform roomInform=new RoomInform(true);

        //设置当前新建房间的房间id
        roomInform.setRoomId(setRoomId);
        //设置当前新建房间的房间状态
        roomInform.setRoomState(0);

        OnlineUser onlineUser;
        //获取当前id的用户
        if(onlineUsers.get(userId)!=null){
            onlineUser=onlineUsers.get(userId);
        }
        else{
            onlineUser=new OnlineUser();
        }

        //设置用户的信息
        onlineUser.setUserInform(userInform);

        //将用户加入房间
        roomInform.getOnlineUserList().put(userId,onlineUser);
        //将房间加入房间列表
        rooms.put(setRoomId,roomInform);

        if(outrooms.get(setRoomId)!=null){
            outrooms.remove(setRoomId);
        }
        //将用户加入房间
        jionRoom(setRoomId,onlineUser);

        //返回当前新建房间的信息给事申请的用户
        return JSONObject.toJSON(roomInform).toString();
    }


    /**
     * 用户加入游戏
     * @param userInform
     * @param roomId
     * @return
     */
    public String joingame(UserInform userInform,Integer roomId){
        String str;
        Integer userId=(int)userInform.getId();


        OnlineUser onlineUser=new OnlineUser();
        onlineUser.setUserInform(userInform);
        if(onlineUsers.get(userId)!=null){
            onlineUser=onlineUsers.get(userId);
         }
        this.jionRoom(roomId,onlineUser);

        RoomInform roomInform=rooms.get(roomId);
        //返回当前新建房间的信息给事申请的用户
        return JSONObject.toJSON(roomInform).toString();
    }

    /**
     * 加入大厅
     */
    public boolean joingameLobby(OnlineUser onlineUser){

        //如果大厅不存则新建一个大厅
        if(rooms.get(0)==null){
            RoomInform roomInform=new RoomInform(true);
            roomInform.setRoomId(0);
            rooms.put(0,roomInform);
        }

      Integer userId=(int) onlineUser.getUserInform().getId();
        if(onlineUsers.get(userId)!=null){
            onlineUser=onlineUsers.get(userId);
        }
      return jionRoom(0,onlineUser);
    }

    /**
     * 用户准备游戏。双方都准备就开始
     * @param roomInform
     */
    public void userReady(RoomInform roomInform){
        //获取房间号
        Integer roomId=roomInform.getRoomId();
        //设置房间中红方的准备状态
        rooms.get(roomId).setRedisReady(roomInform.isRedisReady());
        //设置房间中黑方的准备状态
        rooms.get(roomId).setBlackisReady(roomInform.isBlackisReady());

        //如果房间的双方都 准备，则开始游戏
        if(roomInform.isRedisReady()&&roomInform.isBlackisReady()){
            //改变房间的状态为进行1
            rooms.get(roomId).setRoomState(1);

            //找到房间中，红黑的user改变他们的游戏状态为进行
            TreeMap<Integer,OnlineUser> list=rooms.get(roomId).getOnlineUserList();

            Integer blackId=null;
            Integer redId=null;
            for (Map.Entry<Integer, OnlineUser> entry : list.entrySet()) {

                Integer num=entry.getValue().getUserState();
                if(num==1){
                    redId=entry.getKey();
                    rooms.get(roomId).getOnlineUserList().get(entry.getKey()).setUserState(5);
                }
                else if(num==2){
                    blackId=entry.getKey();
                    rooms.get(roomId).getOnlineUserList().get(entry.getKey()).setUserState(6);
                }
                if(blackId!=null&&redId!=null){
                    break;
                }
            }
            TimeThread timeThread=new TimeThread(roomId,rooms,users);
            timeThread.start();

            //向大厅中的人广播当前房间列表的状态
            String str=JSONObject.toJSON(rooms).toString();
            str+="|joingameLobby";
            //给所有用户广播更新当前大厅的信息
            this.sendMessageToAllUsers(new TextMessage(str));
            //向数据库中插入该对局
            GameInform gameInform=new GameInform();
            //设置红方id
            gameInform.setRedUserId(redId);
            //设置黑方id
            gameInform.setBlackUserId(blackId);
            //设置起始时间
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = simpleDateFormat.format(new Date());
            Timestamp timestamp = Timestamp.valueOf(dateString);
            gameInform.setStartTime(timestamp);
            gameInform.setEndTime(timestamp);

            gameInform.setStepNum(0);
            //设置步时
            gameInform.setSetStepTime(180000);
            //设置局时
            gameInform.setSetTime(1200000);
            //插入数据库
            gameInformService.insert(gameInform);
            //从数据中取回，获得game_id
            List<GameInform> gameInformList=gameInformService.select(gameInform);
            //写回房间信息
            rooms.get(roomId).setGameInform(gameInformList.get(0));

        }
        //向当前房间中的人广播，房间给变的信息
        String str1=JSONObject.toJSON(rooms.get(roomId)).toString();
        str1+="|userReady";
        this.sendMessageToRoomAllUsers(roomId,new TextMessage(str1));

    }

    /**
     * 用户加入房间
     * @param roomId
     * @param onlineUser
     * @return
     */
    public boolean jionRoom(Integer roomId,OnlineUser onlineUser){
        Integer userId=(int)onlineUser.getUserInform().getId();

        Integer oldroomId=onlineUser.getRoomId();
        //如果该用户是刷新用户
//        OnlineUser outOnlineUser=outonlineUsers.get(userId);
//
//        if(outOnlineUser!=null&&outOnlineUser.getRoomId()==roomId){
//            Integer stateNum=outOnlineUser.getUserState();
//            if(rooms.get(roomId)==null&&outrooms.get(roomId)!=null) {
//                rooms.put(roomId,outrooms.get(roomId));
//                outrooms.remove(roomId);
//
//            }
//            if(users.get(userId)==null&&outusers.get(userId)!=null){
//                users.put(userId,outusers.get(userId));
//                outusers.remove(userId);
//            }
//            if(stateNum==1||stateNum==5){//如果原来是红方
//                rooms.get(roomId).setRedName(outOnlineUser.getUserInform().getName());
//
//            }
//            else if(stateNum==2||stateNum==6){//如果原来是黑方
//                rooms.get(roomId).setBlackName(outOnlineUser.getUserInform().getName());
//
//            }
//
//            rooms.get(roomId).getOnlineUserList().put(userId,outOnlineUser);
//            onlineUsers.put(userId,outOnlineUser);
//            outonlineUsers.remove(userId);
//            String str=JSONObject.toJSON(rooms).toString();
//            str+="|joingameLobby";
//            //给所有用户广播更新当前大厅的信息
//            this.sendMessageToAllUsers(new TextMessage(str));
//            //给现在的房间广播
//            if(rooms.get(roomId)!=null){
//                String str3=JSONObject.toJSON(rooms.get(roomId)).toString();
//                str3+="|roomuser";
//                this.sendMessageToRoomAllUsers(roomId,new TextMessage(str3));
//            }
//            return true;
//        }
        //如果该用户已经在对战中，则不能加入其它房间
        if(onlineUser.getUserState()==5||onlineUser.getUserState()==6){
            if(rooms.get(oldroomId).getRoomState()==1){
                return false;
            }
        }
        if(oldroomId!=roomId){
            //如果该用户曾经在某个房间中
            if(oldroomId!=null&&rooms.get(oldroomId)!=null){
                //从这个房间删除该用户，
                rooms.get(oldroomId).getOnlineUserList().remove(userId);
                if(onlineUser.getUserState()==1||onlineUser.getUserState()==5){
                    rooms.get(oldroomId).setRedName("");
                    rooms.get(oldroomId).setRedisReady(false);
                }
                else if(onlineUser.getUserState()==2||onlineUser.getUserState()==6){
                    rooms.get(oldroomId).setBlackName("");
                    rooms.get(oldroomId).setBlackisReady(false);
                }
                //如果删除该用户，房间中没有人，则删除该房间
                if(oldroomId!=0&&rooms.get(oldroomId).getOnlineUserList().size()==0){
                    outrooms.put(oldroomId,rooms.get(oldroomId));
                    rooms.remove(oldroomId);
                }
                //如果原来的房间中依旧有用户，则向该房间的用户广播
                else if(rooms.get(oldroomId).getOnlineUserList().size()>0){
                }
            }
            //然后加入想要加入的房间
            if(rooms.get(roomId)!=null){
                //如要加入大厅则特殊处理
                if(roomId==0){
                    onlineUser.setUserState(4);

                }
                //当红方没人时，自动加入红方
                else if(rooms.get(roomId).getRedName()==null
                        ||rooms.get(roomId).getRedName().equals("")) {
                    onlineUser.setUserState(1);
                    rooms.get(roomId).setRedName(onlineUser.getUserInform().getName());

                }
                //当红有人，黑没人，自动加入黑
                else if(rooms.get(roomId).getBlackName()==null
                        ||rooms.get(roomId).getBlackName().equals("")){
                    onlineUser.setUserState(2);
                    rooms.get(roomId).setBlackName(onlineUser.getUserInform().getName());
                }
                //当红黑都有人，则自动加入观战
                else {
                    onlineUser.setUserState(3);
                }
                onlineUser.setRoomId(roomId);
                rooms.get(roomId).getOnlineUserList().put(userId,onlineUser);
            }
        }
        if(onlineUsers.get(userId)==null){
            onlineUsers.put(userId,onlineUser);
            outonlineUsers.remove(userId);
        }
        String str=JSONObject.toJSON(rooms).toString();
        str+="|joingameLobby";
        //给所有用户广播更新当前大厅的信息
        this.sendMessageToAllUsers(new TextMessage(str));
        //给原先的房间广播
        if(rooms.get(oldroomId)!=null){
            String str1=JSONObject.toJSON(rooms.get(oldroomId)).toString();
            str1+="|roomuserout";
            this.sendMessageToRoomAllUsers(oldroomId,new TextMessage(str1));
        }
        //给现在的房间广播
        if(oldroomId!=roomId&&rooms.get(roomId)!=null){
            String str1=JSONObject.toJSON(rooms.get(roomId)).toString();
            str1+="|roomuser";
            this.sendMessageToRoomAllUsers(roomId,new TextMessage(str1));
        }
        return true;
    }




    /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(Integer clientId, TextMessage message) {
        if (users.get(clientId) == null) return false;
        WebSocketSession session = users.get(clientId);
        System.out.println("sendMessage:" + session);
        if (!session.isOpen()) return false;
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息给指定房间的人
     * @param message
     * @return
     */
    public boolean sendMessageToRoomAllUsers(Integer roomId,TextMessage message) {
        boolean allSendSuccess = true;
        TreeMap<Integer,OnlineUser> onlineUserTreeMap=rooms.get(roomId).getOnlineUserList();
        Set<Integer> clientIds = onlineUserTreeMap.keySet();
        WebSocketSession session = null;
        for (Integer clientId : clientIds) {
            try {
                session = users.get(clientId);
                if(session!=null){
                    if (session.isOpen()) {
                        session.sendMessage(message);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return  allSendSuccess;
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


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");
        users.remove(getClientId(session));
    }


    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("连接已关闭：" + status);
        Integer userId=getClientId(session);
        LoginInform loginInform=new LoginInform();
        loginInform.setUserId(userId);

        List<LoginInform> loginInformList=loginInformService.select(loginInform);
        if(loginInformList.size()>0){
            LoginInform loginInformMax= Collections.max(loginInformList,new Comparator<LoginInform>(){
                public int compare(LoginInform p1, LoginInform p2) {
                    long num1=(long)p1.getLoginTime().getTime();
                    long num2=(long)p2.getLoginTime().getTime();

                    if(num1 > num2){
                        return 1;
                    }
                    else if(num1 == num2){
                        return 0;
                    }
                    return -1;
                }
            });
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = simpleDateFormat.format(new Date());
            Timestamp timestamp = Timestamp.valueOf(dateString);
            loginInformMax.setLogoutTime(timestamp);
            long timeLong=timestamp.getTime()-loginInformMax.getLoginTime().getTime();
            loginInformMax.setTime(timeLong);
            loginInformService.update(loginInformMax);
        }
        this.removeUser(userId);


    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 用户掉线时移除用户
     * @param num
     */
    public Integer removeUser(Integer num){

        outusers.put(num,users.get(num));
        users.remove(num);
        Integer count=-1;
        if(onlineUsers.get(num)!=null){
            OnlineUser onlineUser=onlineUsers.get(num);
            Integer roomId=onlineUser.getRoomId();


            //从这个房间删除该用户，
            rooms.get(roomId).getOnlineUserList().remove(num);
            if(onlineUser.getUserState()==1){
                rooms.get(roomId).setRedName("");

                rooms.get(roomId).setRedisReady(false);
            }
            else if(onlineUser.getUserState()==2){
                rooms.get(roomId).setBlackName("");
                rooms.get(roomId).setBlackisReady(false);
            }
            else if(onlineUser.getUserState()==5&&rooms.get(roomId).getRoomState()==1){//红方退出时，默认投降

                this.gameResult(roomId,7);

            }
            else if(onlineUser.getUserState()==6&&rooms.get(roomId).getRoomState()==1){//黑方退出时，默认投降
                this.gameResult(roomId,6);

            }
            //如果删除该用户，房间中没有人，则删除该房间
            if(roomId!=0&&rooms.get(roomId).getOnlineUserList().size()==0){
                outrooms.put(roomId,rooms.get(roomId));
                rooms.remove(roomId);
            }
            //如果原来的房间中依旧有用户，则向该房间的用户广播
            else{
                String str2=JSONObject.toJSON(rooms.get(roomId)).toString();
                str2+="|roomuserout";
                this.sendMessageToRoomAllUsers(roomId,new TextMessage(str2));
            }
            outonlineUsers.put(num,onlineUsers.get(num));
            onlineUsers.remove(num);
        }
        String str=JSONObject.toJSON(rooms).toString();
        str+="|joingameLobby";
        //给所有用户广播更新当前大厅的信息
        this.sendMessageToAllUsers(new TextMessage(str));
        return count;
    }

    /**
     * 获取用户标识
     * @param session
     * @return
     */
    private Integer getClientId(WebSocketSession session) {
        try {
            UserInform clientId = (UserInform) session.getAttributes().get("userInform");

            return (int)clientId.getId();
        } catch (Exception e) {
            return null;
        }
    }
}