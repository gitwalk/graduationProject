package com.chinesechess.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinesechess.pojo.AdminInform;
import com.chinesechess.pojo.GameInform;
import com.chinesechess.pojo.StepInform;
import com.chinesechess.pojo.UserInform;
import com.chinesechess.service.AdminInformService;
import com.chinesechess.service.GameInformService;
import com.chinesechess.service.StepInformService;
import com.chinesechess.service.UserInformService;
import com.chinesechess.util.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@CrossOrigin("http://127.0.0.1:8080")
@RequestMapping("")
public class GameInformController {

    @Autowired
    GameInformService gameInformService;
    @Autowired
    UserInformService userInformService;
    @Autowired
    StepInformService stepInformService;


    /*查询所有玩家特定游戏的信息*/
    @ResponseBody
    @RequestMapping(value = {"/user/rePlay",
                            "/admin/rePlay"})

    public String rePlay(@RequestBody Integer gameId) {

        String str="error";

        StepInform pojo=new StepInform();
        pojo.setGameId(gameId);

        //查询每一步的信息
        List<StepInform> stepInformList=stepInformService.select(pojo);

        if(stepInformList.size()==0) return  str;

        //按步骤顺序排序
        Collections.sort(stepInformList, new Comparator<StepInform>(){

            public int compare(StepInform p1, StepInform p2) {
                Integer num1=p1.getStepNum();
                Integer num2=p2.getStepNum();

                if(num1 > num2){
                    return 1;
                }
                else if(num1 == num2){
                    return 0;
                }
                return -1;
            }
        });


        str=JSONObject.toJSON(stepInformList).toString();
        System.out.println("arrayList转化后的json："+str);

        return str;
    }

    /*对所有玩家游戏信息查询*/
    @ResponseBody
    @RequestMapping(value = "/admin/listGameInform")
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

    /*对特定玩家游戏信息查询*/
    @ResponseBody
    @RequestMapping(value = "/user/userGameInform")
    public String userGameInform(@RequestBody GameInform gameInform,HttpServletRequest request,
                                 HttpServletResponse response) {

        String str="error";
        HttpSession session =request.getSession();
        UserInform userInform=(UserInform) session.getAttribute("userInform");
        if(userInform==null) {
            return "";
        }
        GameInform pojo=gameInform;
        pojo.setRedUserId(userInform.getId());

        //执行sql语句
        List<GameInform> gameInformList1=gameInformService.select(pojo);

        pojo.setRedUserId(-1);
        pojo.setBlackUserId(userInform.getId());

        //执行sql语句
        List<GameInform> gameInformList2=gameInformService.select(pojo);

        this.setGameInformUserInform(gameInformList1);
        this.setGameInformUserInform(gameInformList2);
        List<GameInform> gameInformList=new ArrayList<>();
        for(GameInform temp :gameInformList1){
            temp.setGameTime(temp.getEndTime().getTime()-temp.getStartTime().getTime());
            gameInformList.add(temp);
        }

        for(GameInform temp :gameInformList2){
            temp.setGameTime(temp.getEndTime().getTime()-temp.getStartTime().getTime());
            gameInformList.add(temp);
        }
        if(gameInformList.size()==0) return "error";
        Collections.sort(gameInformList, new Comparator<GameInform>(){

            public int compare(GameInform p1, GameInform p2) {
                long time1=(long)p1.getStartTime().getTime();
                long time2=(long)p2.getStartTime().getTime();

                if(time1 > time2){
                    return -1;
                }
                else if(time1 == time2){
                    return 0;
                }
                return 1;
            }
        });
        System.out.println("排序后的结果："+gameInformList);


        str= JSONObject.toJSON(gameInformList).toString();
        System.out.println("gameInformList转化后的json："+str);

        return str;
    }


    /*查询所有玩家特定游戏的信息*/
    @ResponseBody
    @RequestMapping(value = "/admin/GameInform")
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
                }
                else if(userInform.getId()==gameInform.getBlackUserId()){
                    gameInform.setBlackUserInform(userInform);
                }
            }
        }
    }


}
