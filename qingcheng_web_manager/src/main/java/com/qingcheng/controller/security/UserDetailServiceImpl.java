package com.qingcheng.controller.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.system.Admin;
import com.qingcheng.service.system.AdminService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UserDetailServiceImpl
 * @Description TODO:
 * @Author guanxin
 * @Date 2020/6/1 22:16
 * @Version 1.0
 */
public class UserDetailServiceImpl implements UserDetailsService {

    @Reference
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {

        System.out.println("UserDetailServiceImpl方法");

        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("loginName",loginName);
        searchMap.put("status",1);

        List<Admin> list = adminService.findList(searchMap);

        if(list.size() == 0){
            return null;
        }

        List<GrantedAuthority> grantedAuthoritieList = new ArrayList<>();
        grantedAuthoritieList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return new User(loginName,list.get(0).getPassword(),grantedAuthoritieList);
    }
}
