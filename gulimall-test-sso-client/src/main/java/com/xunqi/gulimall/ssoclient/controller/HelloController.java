package com.xunqi.gulimall.ssoclient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-29 19:44
 **/

@Controller
public class HelloController {


    /**
     * 无需登录就可访问
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }


    @GetMapping(value = "/employees")
    public String employees(Model model, HttpSession session, @RequestParam(value = "token", required = false) String token) {

        //通过token判断是否登录
        if (!StringUtils.isEmpty(token)) {
            //已经登陆
            RestTemplate restTemplate=new RestTemplate();
            //拿到token，去认证服务器获取该token对应的用户信息，
            //此处不能用openfeign，因为你无法保证认证服务器是java语言
            ResponseEntity<String> forEntity = restTemplate.getForEntity("http://sso.mroldx.cn:8080/userinfo?token=" + token, String.class);
            String body = forEntity.getBody();
            //将该用户存入session中
            session.setAttribute("loginUser", body);
        }
        Object loginUser = session.getAttribute("loginUser");

        if (loginUser == null) {

            return "redirect:" + "http://sso.mroldx.cn:8080/login.html"+"?redirect_url=http://localhost:8081/employees";
        } else {


            List<String> emps = new ArrayList<>();

            emps.add("张三");
            emps.add("李四");

            model.addAttribute("emps", emps);
            return "employees";
        }
    }

}
