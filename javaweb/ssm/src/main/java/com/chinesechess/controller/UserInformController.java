package com.chinesechess.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinesechess.pojo.GameInform;
import com.chinesechess.pojo.LoginInform;
import com.chinesechess.service.GameInformService;
import com.chinesechess.service.LoginInformService;
import com.chinesechess.util.Action;
import com.chinesechess.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.chinesechess.pojo.Test;
import com.chinesechess.pojo.UserInform;
import com.chinesechess.service.UserInformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 告诉spring mvc这是一个控制器类
@Controller
@CrossOrigin("http://127.0.0.1:8080")
@RequestMapping("")
public class UserInformController {

    @Autowired
    UserInformService userInformService;
    @Autowired
    LoginInformService loginInformService;
    @Autowired
    GameInformService gameInformService;

    /*对所有用户信息查询*/
    @ResponseBody
    @RequestMapping(value = "/listUserInform")
    public String listUserInform(@RequestBody Page page) {

        String str="error";

        UserInform pojo=new UserInform();
        //使用PageHelper插件，进行分页
        PageHelper.startPage(page.getPageNum(),page.getCount(),"register_time desc");
        //执行sql语句
        List<UserInform> userInformList=userInformService.select(pojo);
        page.setPageInform(userInformList);

        this.setUserLoginInform_GameNum_winningRate(userInformList);

        List arrayList=new ArrayList();
        arrayList.add(userInformList);
        arrayList.add(page);

        str=JSONObject.toJSON(arrayList).toString();
        System.out.println("arrayList转化后的json："+str);

        return str;
    }

    /*查询特定用户的信息*/
    @ResponseBody
    @RequestMapping(value = "/UserInform")
    public String UserInform(@RequestBody UserInform userInform) {

        String str="error";

        UserInform pojo=userInform;

        //使用PageHelper插件，进行分页
        PageHelper.startPage(userInform.getPage().getPageNum(),userInform.getPage().getCount(),"register_time desc");
        //执行sql语句
        List<UserInform> userInformList=userInformService.select(pojo);

        this.setUserLoginInform_GameNum_winningRate(userInformList);

        Page page=new Page();

        page.setPageInform(userInformList);

        List arrayList=new ArrayList();
        arrayList.add(userInformList);
        arrayList.add(page);

        str=JSONObject.toJSON(arrayList).toString();
        System.out.println("arrayList转化后的json："+str);

        return str;
    }

    /*更新用户信息*/
    @ResponseBody
    @RequestMapping(value = "/updateUserInfrom")
    public String updateUserInfrom(@RequestBody UserInform userInform) {

        String str="error";
        //执行sql语句
        int row=userInformService.update(userInform);
        if(row>0) {
            UserInform pojo=new UserInform();
            pojo.setId(userInform.getId());
            List<UserInform> userInformList=userInformService.select(pojo);
            this.setUserLoginInform_GameNum_winningRate(userInformList);
            str=JSONObject.toJSON(userInformList).toString();
        }
            return str;
    }

    /*获取每个用户的登录信息和游戏次数*/
    public void setUserLoginInform_GameNum_winningRate(List<UserInform> userInformList){
        List<LoginInform> loginInformList=loginInformService.select(new LoginInform());
        List<GameInform> gameInformList=gameInformService.select(new GameInform());

        for(UserInform userInform :userInformList){
            List<LoginInform> userLoginInformList=new ArrayList();
            long gameNum=0;
            long alllongTime=0;
            long gamewinningNum=0;
            for(LoginInform loginInform:loginInformList){
                if(userInform.getId()==loginInform.getUserId()){
                    userLoginInformList.add(loginInform);
                    alllongTime+=loginInform.getTime();
                }
            }
            for(GameInform gameInform:gameInformList){
                if(userInform.getId()==gameInform.getRedUserId()) {
                    if(gameInform.getGameState()%2==0&&gameInform.getGameState()<8){
                        gamewinningNum++;
                    }
                    gameNum++;
                }else if(userInform.getId()==gameInform.getBlackUserId()){
                    if(gameInform.getGameState()%2!=0&&gameInform.getGameState()<8){
                        gamewinningNum++;
                    }
                    gameNum++;
                }
            }

            Collections.sort(userLoginInformList);

            userInform.setLoginNum(userLoginInformList.size());
            userInform.setLoginInformList(userLoginInformList);
            userInform.setAllLoginTime(alllongTime);
            userInform.setGameNum(gameNum);
            if(gameNum==0){
                userInform.setWinningRate(0);
            }else{

                userInform.setWinningRate((double)gamewinningNum/gameNum);
            }


        }
        return ;
    }


}
