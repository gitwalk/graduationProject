package com.chinesechess.controller;

import com.alibaba.fastjson.JSONObject;
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
import java.util.List;

// 告诉spring mvc这是一个控制器类
@Controller
@CrossOrigin("http://127.0.0.1:8080")
@RequestMapping("")
public class UserInformConttroller {
    @Autowired
    UserInformService userInformService;

    /*对所有用户信息查询*/
    @ResponseBody
    @RequestMapping(value = "/listUserInform")
    public String listUserInform(@RequestBody Page page) {

        String str="error";

        UserInform pojo=new UserInform();

        //使用PageHelper插件，进行分页
        PageHelper.startPage(page.getPageNum(),page.getCount());

        //执行sql语句
        List<UserInform> userInformList=userInformService.select(pojo);

        //获取当前分页信息
        PageInfo pageInfo = new PageInfo(userInformList);


        //获取当前的总条数
        page.setPagetotal(pageInfo.getTotal());

        //获取当前分页的总页数
        page.setPages(pageInfo.getPages());

        //获取当前页码
        page.setPageNum(pageInfo.getPageNum());

        List arrayList=new ArrayList();
        arrayList.add(userInformList);
        arrayList.add(page);

        str=JSONObject.toJSON(arrayList).toString();
        System.out.println("arrayList转化后的json："+str);

        return str;
    }

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

            str=JSONObject.toJSON(userInformList).toString();

            }
            return str;



    }


}
