package com.qingcheng.controller.system;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName LoginController
 * @Description TODO: 获取当前登录人
 * @Author guanxin
 * @Date 2020/6/4 21:23
 * @Version 1.0
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /*
     * @Author guanxin
     * @Description //TODO: 获取当前登陆人姓名
     * @Date 21:28 2020/6/4
     * @Param []
     * @return java.util.Map
     **/
    @GetMapping("/showName")
    public Map showName(){

        //获取当前登陆人姓名
        Map map = new HashMap();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        map.put("name",name);
        return map;
    }
}
