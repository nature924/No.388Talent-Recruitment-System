package com.iurac.recruit.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iurac.recruit.entity.*;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.service.DicTypeService;
import com.iurac.recruit.service.DicValueService;
import com.iurac.recruit.service.RoleService;
import com.iurac.recruit.util.Result;
import com.iurac.recruit.util.TableResult;
import com.iurac.recruit.vo.PageResultVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Controller
public class DictionaryController {

    @Autowired
    private DicTypeService dicTypeService;
    @Autowired
    private DicValueService dicValueService;


    @GetMapping("/dictionary/getCityList/{provinceId}")
    @ResponseBody
    public List<City> getRoleList(@PathVariable("provinceId")Integer id, HttpServletRequest request){
        return (List<City>) request.getServletContext().getAttribute("cityInProvince"+id);
    }

    @GetMapping("/dictionary/getBusinessList/{businessId}")
    @ResponseBody
    public List<Business> getBusinessList(@PathVariable("businessId")String id, HttpServletRequest request){
        return (List<Business>) request.getServletContext().getAttribute("businessInType"+id);
    }

    @ResponseBody
    @RequiresRoles("manager")
    @GetMapping("/dictionary/type/get")
    public TableResult<DicType> getDicType(@RequestParam("page")Long page, @RequestParam("limit")Long limit){

        Page<DicType> dicTypePage = new Page<>(page,limit);
        Page<DicType> pageResult = dicTypeService.page(dicTypePage, Wrappers.emptyWrapper());

        return new TableResult(0,"",pageResult.getTotal(),pageResult.getRecords());
    }

    @ResponseBody
    @RequiresRoles("manager")
    @GetMapping("/dictionary/value/get")
    public TableResult<DicValue> getDicValue(@RequestParam("page")Long page, @RequestParam("limit")Long limit){

        PageResultVo<DicValue> pageResultVo = dicValueService.getDicValue(page,limit);

        return new TableResult(0,"",pageResultVo.getTotal(),pageResultVo.getRecords());
    }

    @ResponseBody
    @RequiresRoles("manager")
    @PostMapping("/dictionary/type/delete/{id}")
    public Result deleteType(@PathVariable("id")String id) throws ManageException {
        return dicTypeService.deleteType(id) ? Result.succ("操作成功") : Result.fail("系统错误");
    }

    @ResponseBody
    @RequiresRoles("manager")
    @PostMapping("/dictionary/type/save")
    public Result saveType(DicType dicType) throws ManageException {
        if(StrUtil.hasEmpty(dicType.getType(),dicType.getDescription())){
            return Result.fail("请填写完整信息");
        }

        dicType.setId(IdUtil.simpleUUID());
        return dicTypeService.save(dicType) ? Result.succ("操作成功") : Result.fail("系统错误");
    }

    @ResponseBody
    @RequiresRoles("manager")
    @PostMapping("/dictionary/type/update/{id}")
    public Result updateType(@PathVariable("id")String id,DicType dicType){
        if(StrUtil.hasEmpty(dicType.getType(),dicType.getDescription())){
            return Result.fail("请填写完整信息");
        }
        dicType.setId(id);
        return dicTypeService.updateById(dicType) ? Result.succ("操作成功") : Result.fail("系统错误");
    }

    @ResponseBody
    @RequiresRoles("manager")
    @PostMapping("/dictionary/value/delete/{id}")
    public Result deleteValue(@PathVariable("id")String id) {
        return dicValueService.removeById(id) ? Result.succ("操作成功") : Result.fail("系统错误");
    }

    @ResponseBody
    @RequiresRoles("manager")
    @GetMapping("/dictionary/type/getAll")
    public List<DicType> getAllValue() {
        return dicTypeService.list();
    }

    @ResponseBody
    @RequiresRoles("manager")
    @PostMapping("/dictionary/value/save")
    public Result saveValue(DicValue dicValue) throws ManageException {
        if(StrUtil.hasEmpty(dicValue.getValue(),dicValue.getTypeId()) || dicValue.getOrderNo()<=0){
            return Result.fail("请填写完整信息");
        }

        dicValue.setId(IdUtil.simpleUUID());
        return dicValueService.saveValue(dicValue) ? Result.succ("操作成功") : Result.fail("系统错误");
    }

    @ResponseBody
    @RequiresRoles("manager")
    @PostMapping("/dictionary/value/update/{id}")
    public Result updateValue(@PathVariable("id")String id,DicValue dicValue) throws ManageException {
        if(StrUtil.hasEmpty(dicValue.getValue(),dicValue.getTypeId()) || dicValue.getOrderNo()<=0){
            return Result.fail("请填写完整信息");
        }
        dicValue.setId(id);
        return dicValueService.updateValue(dicValue) ? Result.succ("操作成功") : Result.fail("系统错误");
    }

}

