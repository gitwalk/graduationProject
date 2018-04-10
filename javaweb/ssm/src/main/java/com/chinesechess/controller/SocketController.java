package com.chinesechess.controller;

import com.chinesechess.Handler.MyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpSession;

@Controller


public class SocketController {

    @Autowired
    MyHandler handler;

    @ResponseBody
    @RequestMapping("/so/{userId}")
    public  String login(HttpSession session, @PathVariable("userId") Integer userId) throws Exception {
        System.out.println("登录接口,userId="+userId);
        session.setAttribute("userId", userId);
        session.setAttribute("Num", 1);

        System.out.println("-----------:"+session.getAttribute("userId"));

        return "success";
    }

    @ResponseBody
    @RequestMapping("/message")
    public String sendMessage() {
        boolean hasSend = handler.sendMessageToUser(4, new TextMessage("发送一条小xi"));
        System.out.println(hasSend);
        return "message";
    }

}