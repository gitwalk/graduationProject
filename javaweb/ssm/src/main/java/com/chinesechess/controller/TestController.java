package com.chinesechess.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.chinesechess.pojo.Test;
import com.chinesechess.service.TestService;
import com.chinesechess.util.Page;
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
public class TestController {
    @Autowired
    TestService testService;

    @RequestMapping("listTest")
    public ModelAndView listTest(Page page){
        ModelAndView mav = new ModelAndView();
        PageHelper.offsetPage(page.getStart(),2);
        List<Test> cs= testService.list();

        // 放入转发参数
        mav.addObject("cs", cs);
        // 放入jsp路径
        mav.setViewName("listTest");
        return mav;
    }

    @RequestMapping("submit")
    public ModelAndView submit(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("submit");
        return mav;
    }


    @ResponseBody
    @RequestMapping("/submitTest")
    public String submitCategory(@RequestBody Test test) {
        System.out.println("SSM接受到浏览器提交的json，并转换为Test对象:"+test);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("getOneTest")
    public String getOneCategory() {
        Test c = new Test();
        c.setId(100);
        c.setName("第100个分类");
        JSONObject json= new JSONObject();
        json.put("Test", JSONObject.toJSON(c));
        return json.toJSONString();
    }
    @ResponseBody
    @RequestMapping("getManyTest")
    public String getManyCategory() {
        List<Test> cs = new ArrayList<Test>();
        for (int i = 0; i < 10; i++) {
            Test c = new Test();
            c.setId(i);
            c.setName("分类名称:"+i);
            cs.add(c);
        }
        return JSONObject.toJSON(cs).toString();
    }

}