package com.iurac.recruit.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iurac.recruit.entity.*;
import com.iurac.recruit.exception.ServiceException;
import com.iurac.recruit.security.RedisCacheManager;
import com.iurac.recruit.service.HrService;
import com.iurac.recruit.service.RoleService;
import com.iurac.recruit.service.UserService;
import com.iurac.recruit.util.ImageUtil;
import com.iurac.recruit.util.Result;
import com.iurac.recruit.util.TableResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

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
public class HrController {

    @Autowired
    private RedisCacheManager redisCacheManager;
    @Autowired
    private HrService hrService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @PostMapping("/hr/updateInfo/{id}")
    @RequiresRoles(value = {"hr","manager","admin"},logical = Logical.OR)
    @ResponseBody
    public Result updateInfo(@PathVariable("id")String id, @RequestParam("name")String name, @RequestParam("sex")String sex, @RequestParam("age")String age){
        if(StrUtil.hasBlank(name,sex,age)){
            return Result.fail("请输入完整信息,避免输入空格");
        }

        Hr hr = new Hr();
        hr.setId(id);
        hr.setAge(age);
        hr.setName(name);
        hr.setSex(sex);

        if(!hrService.updateById(hr)){
            return Result.fail("系统错误");
        }

        return Result.succ("操作成功");
    }

    @PostMapping("/hr/update/{id}")
    @RequiresRoles(value = {"manager","admin"},logical = Logical.OR)
    @ResponseBody
    public Result updateInfo(@PathVariable("id")String id, @RequestParam("name")String name,
                             @RequestParam("sex")String sex, @RequestParam("age")String age,@RequestParam("position")String position){
        if(StrUtil.hasBlank(name,sex,age,position)){
            return Result.fail("请输入完整信息,避免输入空格");
        }

        Hr hr = new Hr();
        hr.setId(id);
        hr.setAge(age);
        hr.setName(name);
        hr.setSex(sex);
        hr.setPosition(position);

        if(!hrService.updateById(hr)){
            return Result.fail("系统错误");
        }

        return Result.succ("操作成功");
    }

    @RequiresRoles(value = {"manager","admin"},logical = Logical.OR)
    @ResponseBody
    @GetMapping("/hr/getByConditionInCompany/{id}")
    public TableResult<Hr> getByConditionInCompany(@PathVariable("id")String id, @RequestParam("page")Long page, @RequestParam("limit")Long limit,
                                                   @RequestParam("name")String name, @RequestParam("sex")String sex, @RequestParam("age")String age, @RequestParam("position")String position){

        QueryWrapper<Hr> hrQueryWrapper = new QueryWrapper<>();
        hrQueryWrapper.like("name",name).like("age",age).like("position",position).eq("company_id",id);
        if(!StrUtil.hasBlank(sex)){
            hrQueryWrapper.eq("sex",sex);
        }

        Page<Hr> page1 = new Page<Hr>(page,limit);
        Page<Hr> hrList = hrService.page(page1,hrQueryWrapper);

        return new TableResult(0,"",hrList.getTotal(),hrList.getRecords());
    }

    @PostMapping("/hr/unbind/{hrId}")
    @RequiresRoles(value = {"manager","admin"},logical = Logical.OR)
    @ResponseBody
    public Result unbind(@PathVariable("hrId")String hrId) throws ServiceException {
        hrService.unbind(hrId);
        User user = userService.getById(hrService.getById(hrId).getId());
        redisCacheManager.getCache("authorizationCacheName").remove(user.toString());
        return Result.succ("操作成功");
    }

    @PostMapping("/hr/bind/{companyId}")
    @RequiresRoles(value = {"manager","admin"},logical = Logical.OR)
    @ResponseBody
    public Result bind(@PathVariable("companyId")String companyId,@RequestParam("username")String username,
                       @RequestParam("phone")String phone, @RequestParam("position")String position) throws ServiceException {
        hrService.bind(companyId,username,phone,position);
        return Result.succ("操作成功");
    }

}

