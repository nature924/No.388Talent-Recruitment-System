package com.iurac.recruit.controller;


import com.iurac.recruit.entity.City;
import com.iurac.recruit.entity.Role;
import com.iurac.recruit.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/dictionary/getRoleList")
    @ResponseBody
    public List<Role> getRoleList(){
        List<Role> roleList = roleService.list();
        return roleList;
    }

}

