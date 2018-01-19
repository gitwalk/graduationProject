package com.chinesechess.controller;


import com.alibaba.fastjson.JSONObject;
import com.chinesechess.pojo.AdminInform;
import com.chinesechess.pojo.UserInform;
import com.chinesechess.service.AdminInformService;
import com.chinesechess.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class AdminInformController {

    @Autowired
    AdminInformService adminInformService;

    /*对所有管理员信息查询*/
    @ResponseBody
    @RequestMapping(value = "/listAdminInform")
    public String listAdminInform(@RequestBody Page page) {

        String str="error";

        AdminInform pojo=new AdminInform();

        //使用PageHelper插件，进行分页
        PageHelper.startPage(page.getPageNum(),page.getCount(),"register_time desc");

        //执行sql语句
        List<AdminInform> adminInformList=adminInformService.select(pojo);

        //获取分页信息
        page.setPageInform(adminInformList);

        List arrayList=new ArrayList();
        arrayList.add(adminInformList);
        arrayList.add(page);

        str= JSONObject.toJSON(arrayList).toString();
        System.out.println("arrayList转化后的json："+str);

        return str;
    }


    /*查询特定管理员的信息*/
    @ResponseBody
    @RequestMapping(value = "/AdminInform")
    public String AdminInform(@RequestBody AdminInform adminInform) {

        String str="error";

        AdminInform pojo=adminInform;

        //使用PageHelper插件，进行分页
        PageHelper.startPage(adminInform.getPage().getPageNum(),adminInform.getPage().getCount(),"register_time desc");

        //执行sql语句
        List<AdminInform> adminInformList=adminInformService.select(pojo);

        Page page=new Page();

        page.setPageInform(adminInformList);

        List arrayList=new ArrayList();
        arrayList.add(adminInformList);
        arrayList.add(page);

        str=JSONObject.toJSON(arrayList).toString();
        System.out.println("arrayList转化后的json："+str);

        return str;
    }

    /*更新管理员信息*/
    @ResponseBody
    @RequestMapping(value = "/updateAdminInfrom")
    public String updateAdminInfrom(@RequestBody AdminInform adminInform) {

        String str="error";
        //执行sql语句
        int row=adminInformService.update(adminInform);
        if(row>0) {
            AdminInform pojo=new AdminInform();
            pojo.setId(adminInform.getId());
            List<AdminInform> adminInformList=adminInformService.select(pojo);

            str=JSONObject.toJSON(adminInformList).toString();
        }
        return str;
    }


}
