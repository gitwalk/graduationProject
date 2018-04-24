package com.chinesechess.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinesechess.Handler.MyHandler;
import com.chinesechess.pojo.*;
import com.chinesechess.service.LoginInformService;
import com.chinesechess.service.SessionInformService;
import com.chinesechess.service.UserInformService;
import com.chinesechess.util.MessageInform;
import com.chinesechess.util.OnlineUser;
import com.chinesechess.util.RoomInform;
import com.chinesechess.util.SubmitStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin("*")
@RequestMapping("")
public class SocketController {


    @Autowired
    MyHandler handler;
    @Autowired
    UserInformService userInformService;
    @Autowired
    LoginInformService loginInformService;
    //获取用户信息
    @RequestMapping(value = "/user/getuserInform")
    @ResponseBody
    public  String getuserInform(HttpServletRequest request, HttpServletResponse response){
        HttpSession session =request.getSession();
        UserInform userInform=(UserInform)session.getAttribute("userInform");
        String str="error";
        if(userInform!=null){

                str=JSONObject.toJSON(userInform).toString();

        }

        return  str;

    }

    /*退出登录设置session*/
    @ResponseBody
    @RequestMapping(value = "/logoutUserInfrom")
    public String logoutUserInfrom(HttpServletRequest request,
                                    HttpServletResponse response) {

        HttpSession session =request.getSession();
        UserInform userInform=(UserInform) session.getAttribute("userInform");
//        if(userInform!=null){
//            handler.removeUser((int)userInform.getId());
//        }

        session.setAttribute("userInform", null);
        return "success";
    }

    //登录游戏
    @RequestMapping(value = "/loginUserInfrom")
    @ResponseBody
    public  String loginUserInfrom(@RequestBody UserInform userInform,HttpServletRequest request,
                         HttpServletResponse response) throws Exception {


        String str="error";
        HttpSession session =request.getSession();

        UserInform pojo=userInform;

        List<UserInform> userInformList=userInformService.select(pojo);

        UserInform olduserInform=(UserInform) session.getAttribute("userInform");

        if(userInformList.size()>0){

            if(userInformList.get(0).getIsDeleted()==1){
                str="isDelete";
            }
            else {
                if(olduserInform!=null) {
                    session.setAttribute("olduserInform",olduserInform);
                }
                //设置session
                session.setAttribute("userInform",userInformList.get(0));
                str=JSONObject.toJSON(userInformList.get(0)).toString();
                //插入登录信息
                LoginInform loginInform=new LoginInform();
                loginInform.setLoginIp(request.getRemoteAddr());
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = simpleDateFormat.format(new Date());
                Timestamp timestamp = Timestamp.valueOf(dateString);
                loginInform.setLoginTime(timestamp);
                loginInform.setUserId(userInformList.get(0).getId());
                loginInformService.insert(loginInform);

                System.out.println("登录信息："+str);
                System.out.println("++++++++++++++++："+session.getId());
            }

        }
        return str;

    }



    //提交游戏结果
    @ResponseBody
    @RequestMapping("/user/gameResult")
    public String gameResult(@RequestBody Integer num, HttpServletRequest request,
                                   HttpServletResponse response){

        Integer roomId=num/10;
        Integer resultNum=num%10;

        handler.gameResult(roomId,resultNum);
        return "gameResult";
    }

    //处理求和结果
    @ResponseBody
    @RequestMapping("/user/dealpace")
    public String dealpace(@RequestBody Integer num, HttpServletRequest request,
                             HttpServletResponse response){

        Integer roomId=num/100;
        Integer paceNum=(num%100)/10;
        Integer resultNum=num%10;

        handler.dealpace(roomId,paceNum,resultNum);
        return "dealpace";
    }


    //提交每一步信息
    @ResponseBody
    @RequestMapping("/user/submitStepinform")
    public String submitStepinform(@RequestBody SubmitStep submitStep, HttpServletRequest request,
                                   HttpServletResponse response){

        handler.dealStepinform(submitStep);
        return "submitStepinform";
    }

    //加入游戏
    @ResponseBody
    @RequestMapping("/user/joingame")
    public String joingame(@RequestBody Integer roomId, HttpServletRequest request,
                            HttpServletResponse response){
        String str="erroe";
        UserInform userInform=(UserInform) request.getSession().getAttribute("userInform");
        if(userInform!=null){
            str = handler.joingame(userInform,roomId);
            System.out.println(str);
        }
        return  str;
    }



    //玩家准备
    @ResponseBody
    @RequestMapping("/user/userReady")
    public String userReady(@RequestBody RoomInform roomInform, HttpServletRequest request,
                            HttpServletResponse response) {
        String str="erroe";
        UserInform userInform=(UserInform) request.getSession().getAttribute("userInform");
        if(userInform!=null){

            handler.userReady(roomInform);
            System.out.println(str);
        }

        return str;
    }

    //新建房间的接口
    @ResponseBody
    @RequestMapping("/user/buildRoom")
    public String buildRoom(@RequestBody RoomInform roomInform, HttpServletRequest request,
                                   HttpServletResponse response) {
        String str="erroe";
        UserInform userInform=(UserInform) request.getSession().getAttribute("userInform");
        if(userInform!=null){

            str = handler.addRoom(userInform);
            System.out.println(str);
        }

        return str;
    }

    //加入大厅的接口
    @ResponseBody
    @RequestMapping(value = "/user/togameLobby")
    public String togameLobby(@RequestBody OnlineUser onlineUser, HttpServletRequest request,
                              HttpServletResponse response) {

        UserInform userInform=(UserInform) request.getSession().getAttribute("userInform");

        if(userInform!=null) {

            onlineUser.setUserInform(userInform);
            boolean hasSend = handler.joingameLobby(onlineUser);
            System.out.println(hasSend);
            if(!hasSend){
                return "error";
            }
        }
        return "togameLobby";

    }

    @ResponseBody
    @RequestMapping("/user/message")
    public String sendMessage() {
        boolean hasSend = handler.sendMessageToUser(4,new TextMessage("你好"));
        System.out.println(hasSend);
        return "message";
    }

    @ResponseBody
    @RequestMapping("/user/allmessage")
    public String sendMessagetoall(@RequestBody MessageInform messageInform, HttpServletRequest request,
                                   HttpServletResponse response) {
        String str=JSONObject.toJSON(messageInform).toString();
        str+="|messageInform";
        TextMessage textMessage=new TextMessage(str);
        boolean hasSend = handler.sendMessageToRoomAllUsers(messageInform.getRoomId(),textMessage);
        System.out.println(hasSend);
        return "message";
    }

}