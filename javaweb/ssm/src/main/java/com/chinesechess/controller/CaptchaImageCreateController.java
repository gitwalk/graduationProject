package com.chinesechess.controller;


import java.awt.image.BufferedImage;
import java.io.IOException;

import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinesechess.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Controller
@CrossOrigin
@RequestMapping("/captcha")
public class CaptchaImageCreateController {

    private Producer captchaProducer = null;

    @Autowired
    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @ResponseBody
    @RequestMapping("/sendMailcode")
    public String AjaxSendIdCode(@RequestBody String mail, HttpServletResponse response,HttpServletRequest request)
            throws IOException, AddressException, MessagingException {

        response.setCharacterEncoding("utf-8");
        //生成随机验证码
        int idcode = (int) (Math.random()*100000);
        String text = Integer.toString(idcode);


        MailUtil mailUtil = new MailUtil();
//        String textstr="亲爱的玩家\n" +
//                "还差一步，就能踏上象棋对战之路啦。\n" +
//                "快！输入下面的验证码吧。\n"+
//                "验证码："+text;
        mailUtil.sendMail(mail, "注册验证码："+text+"(有效时间10分钟)");

        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        Long date = calendar.getTime().getTime();
        String nowTime=date.toString();

        //存入session  验证码#时间
        HttpSession session = request.getSession();
        session.setAttribute("vcodeTime",text+"#"+nowTime+"#"+mail);
        System.out.println(session.getAttribute("vcodeTime"));
        return "success";
    }

    @ResponseBody
    @RequestMapping("/sendMailcode1")
    public String AjaxSendIdCode1(@RequestBody String mail, HttpServletResponse response,HttpServletRequest request)
            throws IOException, AddressException, MessagingException {

        response.setCharacterEncoding("utf-8");
        //生成随机验证码
        int idcode = (int) (Math.random()*100000);
        String text = Integer.toString(idcode);


        MailUtil mailUtil = new MailUtil();
//        String textstr="亲爱的玩家\n" +
//                "还差一步，就能踏上象棋对战之路啦。\n" +
//                "快！输入下面的验证码吧。\n"+
//                "验证码："+text;

        mailUtil.sendMail(mail, "密码找回验证码："+text+"(有效时间10分钟)");

        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        Long date = calendar.getTime().getTime();
        String nowTime=date.toString();

        //存入session  验证码#时间
        HttpSession session = request.getSession();
        session.setAttribute("vcodeTime1",text+"#"+nowTime+"#"+mail);
        System.out.println(session.getAttribute("vcodeTime1"));
        return "success";
    }

    @ResponseBody
    @RequestMapping("/checkMailcode1")
    public String checkMailcode1(@RequestBody String vcode,HttpServletRequest request){
        String vcodeTime=(String)request.getSession().getAttribute("vcodeTime1");
        String vcodeArray[] =vcode.split("#");
        vcode=vcodeArray[0];
        if(vcodeArray.length<2) return "error";
        String vMail=vcodeArray[1];
        if(vcodeTime!=null){
            String vcodeTimeArray[] =vcodeTime.split("#");
            if(vcodeTimeArray[0].equals(vcode)&&vcodeTimeArray[2].equals(vMail)) {
                if(this.cmpTime(vcodeTimeArray[1])){
                    return "success";
                }
                else {
                    return "timeout";
                }

            }
        }
        return "error";
    }

    @ResponseBody
    @RequestMapping("/checkMailcode")
    public String checkMailcode(@RequestBody String vcode,HttpServletRequest request){
        String vcodeTime=(String)request.getSession().getAttribute("vcodeTime");
        String vcodeArray[] =vcode.split("#");
        vcode=vcodeArray[0];
        if(vcodeArray.length<2) return "error";
        String vMail=vcodeArray[1];
        if(vcodeTime!=null){
            String vcodeTimeArray[] =vcodeTime.split("#");
            if(vcodeTimeArray[0].equals(vcode)&&vcodeTimeArray[2].equals(vMail)) {
                if(this.cmpTime(vcodeTimeArray[1])){
                    return "success";
                }
                else {
                    return "timeout";
                }

            }
        }
        return "error";
    }

    @RequestMapping("/captcha-image")
    public ModelAndView handleRequest(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        response.setDateHeader("Expires", 0);

        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        response.setHeader("Pragma", "no-cache");
        // 设置返回的类型
        response.setContentType("image/png");
        // 创造随机验证码
        String capText = captchaProducer.createText();
        // 将生成的文字，存入session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // 将生成的text，转化为文件
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        //输出数据
        ImageIO.write(bi, "png", out);
        System.out.println("************************产生的随机验证码是："+capText);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    /*检查登录验证码的正确性*/
    @ResponseBody
    @RequestMapping(value = "/loginMark")
    public String logoutUserInfrom(@RequestBody String Mark, HttpServletRequest request,
                                   HttpServletResponse response, HttpSession session) {


        String code = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if(Mark.equals(code)){
            return "success";
        }
        return "error";

    }

    public boolean cmpTime(String time) {
        System.out.println("cmpTime...util...");
        long tempTime = Long.parseLong(time);
        System.out.println("tempTime"+tempTime);

        //在获取现在的时间
        Calendar calendar = Calendar.getInstance();
        Long date = calendar.getTime().getTime();            //获取毫秒时间
        System.out.println("date"+date);

        if(date - tempTime > 600000 ) {   //10收分钟
            return false;
        } else {
            return true;
        }

    }


}  