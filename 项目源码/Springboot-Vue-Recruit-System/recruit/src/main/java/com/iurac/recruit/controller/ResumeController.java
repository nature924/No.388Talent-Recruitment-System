package com.iurac.recruit.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iurac.recruit.entity.Company;
import com.iurac.recruit.entity.Resume;
import com.iurac.recruit.entity.User;
import com.iurac.recruit.exception.ManageException;
import com.iurac.recruit.exception.ServiceException;
import com.iurac.recruit.service.ResumeService;
import com.iurac.recruit.util.ImageUtil;
import com.iurac.recruit.util.Result;
import com.iurac.recruit.util.TableResult;
import com.iurac.recruit.vo.PageResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;


    @PostMapping("/resume/saveOrUpdate")
    @ResponseBody
    public Result saveOrUpdate(Resume resume,
                                 @RequestParam(value="imgFile",required = false) MultipartFile file) throws Exception {

        if(StrUtil.hasBlank(resume.getName(),resume.getTechnology(),resume.getSalary(), resume.getMajor(),resume.getJob(),
                resume.getIntroduction(),resume.getExperience(),resume.getCollege(),resume.getBirth(),resume.getEducation(),
                resume.getPolitical(),resume.getMarriage(),resume.getSex())){
            return Result.fail("请输入完成信息,避免输入空格");
        }
        if(!ReUtil.isMatch("(13\\d|14[579]|15[^4\\D]|17[^49\\D]|18\\d)\\d{8}", resume.getPhone())) {
            return Result.fail("请输入正确的手机号");
        }
        if(!ReUtil.isMatch("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}", resume.getEmail())){
            return Result.fail("请输入正确的邮箱地址");
        }

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        resume.setUserId(user.getId());

        QueryWrapper<Resume> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        Resume exitedResume = resumeService.getOne(queryWrapper);
        resume.setPhoto("default.png");

        boolean isUpdate = true;
        if(ObjectUtil.isNull(exitedResume)){//判断是新增还是更新
            resume.setId(IdUtil.simpleUUID());
            isUpdate = false;
        }else {
            resume.setId(exitedResume.getId());
            resume.setPhoto(exitedResume.getPhoto());
        }

        if(ObjectUtil.isNotNull(file) && !file.isEmpty()){
            String originalFileName = file.getOriginalFilename();
            String photoName = resume.getPhoto().equals("default.png")?ImageUtil.getFileName(originalFileName.substring(originalFileName.lastIndexOf("."))): resume.getPhoto();
            resume.setPhoto(photoName);
            ImageUtil.saveImage(file,resume.getPhoto(),"photo");
        }

        boolean flag = isUpdate?resumeService.updateById(resume):resumeService.save(resume);

        if(flag){
            return Result.succ("操作成功");
        }else {
            return Result.fail("系统错误");
        }
    }


    @RequiresRoles("admin")
    @ResponseBody
    @GetMapping("/resume/getByCondition")
    public TableResult<Resume> getByCondition(@RequestParam("page")Long page, @RequestParam("limit")Long limit,
                                               @RequestParam("username")String username){
        PageResultVo<Resume> pageResultVo = resumeService.getByCondition(page,limit,username);
        return new TableResult(0,"",pageResultVo.getTotal(),pageResultVo.getRecords());
    }

    @RequiresRoles("admin")
    @ResponseBody
    @PostMapping("/resume/update/{id}")
    public Result updateResume(@PathVariable("id")String id, @RequestParam(value = "imgFile",required = false) MultipartFile file,
                               Resume resume) throws IOException {
        if(StrUtil.hasEmpty(resume.getSex(),resume.getMarriage(),resume.getPolitical(),resume.getEducation(),
                resume.getEmail(),resume.getAge(),resume.getPhone(),resume.getName(),resume.getExperience(),
                resume.getTechnology(),resume.getSalary(),resume.getMajor(),resume.getIntroduction(),resume.getCollege(),
                resume.getBirth(),resume.getJob())){
            return Result.fail("请输入完整信息,避免输入空格");
        }

        Resume exitedResume = resumeService.getById(id);
        resume.setId(id);
        resume.setUserId(exitedResume.getUserId());
        resume.setPhoto(exitedResume.getPhoto());
        if(ObjectUtil.isNotNull(file) && !file.isEmpty()){
            String originalFileName = file.getOriginalFilename();
            resume.setPhoto(ImageUtil.getFileName(originalFileName.substring(originalFileName.lastIndexOf("."))));
//            ImageUtil.saveImage(file,resume.getPhoto(),"companyIcon");
            ImageUtil.saveImage(file,resume.getPhoto(),"photo");
        }
        return resumeService.updateById(resume)?Result.succ("操作成功"):Result.fail("操作失败");
    }

    @RequiresRoles("admin")
    @ResponseBody
    @PostMapping("/resume/delete/{id}")
    public Result deleteResume(@PathVariable("id")String id) {
        resumeService.removeById(id);
        return Result.succ("操作成功");
    }

    @RequiresUser
    @GetMapping("/resume/detail")
    public String detail(Model model) throws ServiceException {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Resume resume = resumeService.getByUserId(user.getId());
        if(ObjectUtil.isNull(resume)){
            throw new ServiceException("找不到您的简历!");
        }
        model.addAttribute("resumeInfo",resume);
        return "service/detail_resume";
    }
}

