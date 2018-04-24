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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @RequestMapping(value = "/user/selectUserInform")
    public String selectUserInform( HttpServletRequest request, HttpServletResponse response) {

        String str="error";
        UserInform pojo=(UserInform) request.getSession().getAttribute("userInform");

        //执行sql语句
        List<UserInform> userInformList=userInformService.select(pojo);


        this.setUserLoginInform_GameNum_winningRate(userInformList);

        str=JSONObject.toJSON(userInformList).toString();
        System.out.println("arrayList转化后的json："+str);

        return str;
    }



    /*对所有用户信息查询*/
    @ResponseBody
    @RequestMapping(value = "/admin/listUserInform")
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
    @RequestMapping(value = "/admin/UserInform")
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

    /*添加用户信息*/
    @ResponseBody
    @RequestMapping(value = "/addUser")
    public String addUser(@RequestBody UserInform userInform) {

        String str="error";
        UserInform pojo=userInform;
        //设置是否禁用
        pojo.setIsDeleted(0);

        //设置当前时间
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(new Date());
        pojo.setRegisterTime(dateString);
        //设置权限
        pojo.setRole(0);
        //设置性别为保密
        pojo.setSex(2);
        Integer row=userInformService.insert(userInform);
        if(row>0) str=row.toString();

        return str;
    }

    /*检查用户信息是否存在*/
    @ResponseBody
    @RequestMapping(value = "/checkInform")
    public String checkInform(@RequestBody UserInform userInform) {

        String str="error";
        //执行sql语句
        Integer row=userInformService.select(userInform).size();

        return row.toString();
    }

    /*更新用户性别*/
    @ResponseBody
    @RequestMapping(value = "/user/updataUsersex")
    public String updataUsersex(@RequestBody UserInform userInform,HttpServletRequest request, HttpServletResponse response) {

        String str="error";
        Integer userId=(int)((UserInform) request.getSession().getAttribute("userInform")).getId();

        UserInform temp=new UserInform();
        temp.setId(userId);
        temp.setSex(userInform.getSex());

        Integer row=userInformService.update(temp);
        if(row>0){
            str=row.toString();
        }
        return str;
    }

    /*检查原密码*/
    @ResponseBody
    @RequestMapping(value = "/user/checkPassWord")
    public String checkPassWord(@RequestBody UserInform userInform,HttpServletRequest request, HttpServletResponse response) {

        String str="error";
        Integer userId=(int)((UserInform) request.getSession().getAttribute("userInform")).getId();

        UserInform temp=new UserInform();
        temp.setId(userId);
        temp.setPassword(userInform.getPassword());

        Integer row=userInformService.select(temp).size();
        if(row>0){
            str=row.toString();
        }
        return str;
    }

    /*更新用户密码*/
    @ResponseBody
    @RequestMapping(value = "/user/updataUserPassword")
    public String userUpdataUserPassword(@RequestBody UserInform userInform,HttpServletRequest request, HttpServletResponse response)  {

        String str="error";
        Integer userId=(int)((UserInform) request.getSession().getAttribute("userInform")).getId();

        UserInform temp=new UserInform();
        temp.setId(userId);
        temp.setPassword(userInform.getPassword());

        Integer row=userInformService.update(temp);
        if(row>0){
            str=row.toString();
        }
        return str;
    }


    /*重置用户密码*/
    @ResponseBody
    @RequestMapping(value = "/updataUserPassword")
    public String updataUserPassword(@RequestBody UserInform userInform) {

        String str="error";
        UserInform pojo=new UserInform();
        pojo.setName(userInform.getName());
        pojo.setEmailAddress(userInform.getEmailAddress());

        //查询用户的id
        List<UserInform> userInformList=userInformService.select(pojo);

        Integer userId=(int)userInformList.get(0).getId();
        UserInform temp=new UserInform();
        temp.setId(userId);
        temp.setPassword(userInform.getPassword());

        Integer row=userInformService.update(temp);
        if(row>0){
            str=row.toString();
        }
        return str;
    }

    /*更新用户信息*/
    @ResponseBody
    @RequestMapping(value = "/admin/updateUserInfrom")
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
