package com.chinesechess.controller;


import com.alibaba.fastjson.JSONObject;
import com.chinesechess.pojo.AdminInform;
import com.chinesechess.pojo.UserInform;
import com.chinesechess.service.AdminInformService;
import com.chinesechess.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;





@Controller
@CrossOrigin("http://127.0.0.1:8080")
@RequestMapping("")
public class AdminInformController {

    @Autowired
    AdminInformService adminInformService;

    /*对所有管理员信息查询*/
    @ResponseBody
    @RequestMapping(value = "/admin/listAdminInform")
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
    @RequestMapping(value = "/admin/AdminInform")
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
    @RequestMapping(value = "/admin/updateAdminInfrom")
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

    /*获取session的信息*/
    @ResponseBody
    @RequestMapping(value = "/admin/getSession")
    public String updateAdminInfrom(HttpServletRequest request,
                                    HttpServletResponse response, Object handler) {

        String str="error";
        HttpSession session =request.getSession();
        Object obj = session.getAttribute("admin");
        if(obj!=null){
            str=JSONObject.toJSON(obj).toString();
        }
        return str;
    }

    /*登录设置session*/
    @ResponseBody
    @RequestMapping(value = "/loginAdminInfrom")
    public String loginAdminInfrom(@RequestBody AdminInform adminInform, HttpServletRequest request,
                                   HttpServletResponse response) {

        String str="error";
        HttpSession session =request.getSession();
        AdminInform pojo=adminInform;
        List<AdminInform> adminInformList=adminInformService.selectF(pojo);
        for(AdminInform ad:adminInformList){
            if(ad.getIsDeleted()==1){
                return "disable";
            }
        }
        if(adminInformList.size()>0){
            session.setAttribute("admin", adminInformList);
            str=JSONObject.toJSON(adminInformList).toString();

        }

        return str;
    }

    /*退出登录设置session*/
    @ResponseBody
    @RequestMapping(value = "/admin/logoutAdminInfrom")
    public String logoutAdminInfrom( HttpServletRequest request,
                                   HttpServletResponse response, Object handler) {

        HttpSession session =request.getSession();

        session.setAttribute("admin", null);
        return "success";
    }

    /*检查设置的name是否唯一*/
    @ResponseBody
    @RequestMapping(value = "/admin/checkAdminName")
    public String checkAdminName(@RequestBody AdminInform adminInform, HttpServletRequest request,
                                   HttpServletResponse response, Object handler) {

        String str="error";

        AdminInform pojo=new AdminInform();
        pojo.setName(adminInform.getName());
        List<AdminInform> adminInformList=adminInformService.selectF(pojo);
        if(adminInformList.size()==0){
            str="success";
        }
        return str;
    }

    /*添加管理员*/
    @ResponseBody
    @RequestMapping(value = "/admin/addAdminInfrom")
    public String addAdminInfrom(@RequestBody AdminInform adminInform, HttpServletRequest request,
                                 HttpServletResponse response, Object handler) {

        String str="error";
        AdminInform pojo=adminInform;
        pojo.setIsDeleted(0);
        pojo.setRole(1);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pojo.setRegisterTime(simpleDateFormat.format(date));


        int row=adminInformService.insert(pojo);
        if(row>0){
            str="success";
        }
        return str;
    }

}
