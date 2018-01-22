package com.chinesechess.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinesechess.pojo.AdminInform;
import com.chinesechess.pojo.GameInform;
import com.chinesechess.pojo.UserInform;
import com.chinesechess.service.AdminInformService;
import com.chinesechess.service.GameInformService;
import com.chinesechess.service.UserInformService;
import com.chinesechess.util.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("http://127.0.0.1:8080")
@RequestMapping("")
public class GameInformController {

    @Autowired
    GameInformService gameInformService;
    @Autowired
    UserInformService userInformService;

    /*对所有管理员信息查询*/
    @ResponseBody
    @RequestMapping(value = "/listGameInform")
    public String listGameInform(@RequestBody Page page) {

        String str="error";

        GameInform pojo=new GameInform();

        //使用PageHelper插件，进行分页
        PageHelper.startPage(page.getPageNum(),page.getCount(),"start_time desc");

        //执行sql语句
        List<GameInform> gameInformList=gameInformService.select(pojo);

        //获取分页信息
        page.setPageInform(gameInformList);

        this.setGameInformUserInform(gameInformList);

        for(GameInform gameInform :gameInformList){
            gameInform.setGameTime(gameInform.getEndTime().getTime()-gameInform.getStartTime().getTime());
        }

        List arrayList=new ArrayList();
        arrayList.add(gameInformList);
        arrayList.add(page);

        str= JSONObject.toJSON(arrayList).toString();
        System.out.println("arrayList转化后的json："+str);

        return str;
    }

    /*查询特定管理员的信息*/
    @ResponseBody
    @RequestMapping(value = "/GameInform")
    public String GameInform(@RequestBody GameInform gameInform) {

        String str="error";

        GameInform pojo=gameInform;

        //使用PageHelper插件，进行分页
        PageHelper.startPage(gameInform.getPage().getPageNum(),gameInform.getPage().getCount(),"start_time desc");

        //执行sql语句
        List<GameInform> gameInformList=gameInformService.select(pojo);

        this.setGameInformUserInform(gameInformList);

        Page page=new Page();

        page.setPageInform(gameInformList);

        for(GameInform gameInform1 :gameInformList){
            gameInform1.setGameTime(gameInform1.getEndTime().getTime()-gameInform1.getStartTime().getTime());
        }

        List arrayList=new ArrayList();
        arrayList.add(gameInformList);
        arrayList.add(page);

        str=JSONObject.toJSON(arrayList).toString();
        System.out.println("arrayList转化后的json："+str);

        return str;
    }
    /*获取红黑方的信息*/
    public void setGameInformUserInform(List<GameInform> gameInformList){

        UserInform pojo=new UserInform();
        List<UserInform> userInformList=userInformService.select(pojo);
        for (GameInform gameInform :gameInformList){
            for(UserInform userInform:userInformList){
                if(userInform.getId()==gameInform.getRedUserId()){
                    gameInform.setRedUserInform(userInform);
                }else if(userInform.getId()==gameInform.getBlackUserId()){
                    gameInform.setBlackUserInform(userInform);
                }
            }
        }
    }


}
