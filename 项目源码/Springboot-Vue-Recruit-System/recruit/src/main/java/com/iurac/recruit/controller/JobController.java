package com.iurac.recruit.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iurac.recruit.entity.*;
import com.iurac.recruit.exception.ServiceException;
import com.iurac.recruit.service.CompanyService;
import com.iurac.recruit.service.HrService;
import com.iurac.recruit.service.JobService;
import com.iurac.recruit.util.Result;
import com.iurac.recruit.util.TableResult;
import com.iurac.recruit.vo.EchartsVo;
import com.iurac.recruit.vo.JobVo;
import com.iurac.recruit.vo.PageResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.awt.print.Book;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@Controller
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private HrService hrService;
    @Autowired
    private CompanyService companyService;

    @GetMapping("/job/getJobsByConditionInPage")
    @RequiresUser
    @ResponseBody
    public Map<String,Object> getJobsByConditionInPage(@RequestParam(value="page")int page,
                                                       @RequestParam(value="jobName") String jobName,
                                                       @RequestParam(value="education") String education,
                                                       @RequestParam(value="experience") String experience,
                                                       @RequestParam(value="createTime",defaultValue = "0") Long createTime,
                                                       @RequestParam(value="city") String city,
                                                       @RequestParam(value="business") String business) throws IOException {
        String startCreateTime = "";
        if(createTime !=0){
            Date date = DateUtil.date(DateUtil.date().getTime()-createTime*24*60*60*1000);
            startCreateTime = DateUtil.format(date, "yyyy-MM-dd");
        }
        List<JobVo> jobs = jobService.getJobsByConditionInPage(page*12,jobName,education,experience,startCreateTime,city,business);
        Map<String,Object> map = new HashMap<>();
        map.put("count",jobs.size());
        map.put("jobs",jobs);
        return  map;
    }

    @RequiresRoles(value = {"manager","admin"},logical = Logical.OR)
    @ResponseBody
    @GetMapping("/job/getByConditionInCompany/{id}")
    public TableResult<Job> getByConditionInCompany(@PathVariable("id")String id, @RequestParam("page")Long page, @RequestParam("limit")Long limit,
                                                    @RequestParam("name")String name, @RequestParam("education")String education,
                                                    @RequestParam("startDate")String startDate, @RequestParam(value = "endDate",required = false)String endDate,
                                                    @RequestParam("area")String area, @RequestParam("business")String business){

        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper.eq("company_id",id)
                .like("job",name)
                .like("education",education)
                .like("area",area)
                .like("business",business);
        if(!StrUtil.hasBlank(endDate)){
            jobQueryWrapper.between("create_time",startDate,endDate);
        }

        Page<Job> page1 = new Page<>(page,limit);
        Page<Job> jobPage = jobService.page(page1,jobQueryWrapper);

        return new TableResult(0,"",jobPage.getTotal(),jobPage.getRecords());
    }

    @RequiresRoles(value = {"manager","hr","admin"},logical = Logical.OR)
    @ResponseBody
    @GetMapping("/job/getByConditionInHr/{id}")
    public TableResult<Job> getByConditionInHr(@PathVariable("id")String id, @RequestParam("page")Long page, @RequestParam("limit")Long limit){

        QueryWrapper<Job> jobQueryWrapper = new QueryWrapper<>();
        jobQueryWrapper.eq("create_hr_id",id);

        Page<Job> jobPage = new Page<>(page,limit);
        Page<Job> pageResult = jobService.page(jobPage,jobQueryWrapper);

        return new TableResult(0,"",pageResult.getTotal(),pageResult.getRecords());
    }

    @PostMapping("/job/unpublish/{jobId}")
    @RequiresRoles(value = {"manager","admin"},logical = Logical.OR)
    @ResponseBody
    public Result unpublish(@PathVariable("jobId")String jobId){
        return jobService.removeById(jobId)?Result.succ("操作成功"):Result.fail("系统错误");
    }


    @PostMapping("/job/update/{id}")
    @RequiresRoles(value = {"manager","admin"},logical = Logical.OR)
    @ResponseBody
    public Result updateInfo(@PathVariable("id")String id, Job job){
        return jobService.updateById(job)?Result.succ("操作成功"):Result.fail("系统错误");
    }

    @PostMapping("/job/publish/{companyId}/{hrId}")
    @RequiresRoles(value = {"manager","hr","admin"},logical = Logical.OR)
    @ResponseBody
    public Result publish(@PathVariable("companyId")String companyId,@PathVariable("hrId")String hrId, Job job){
        job.setId(IdUtil.simpleUUID());
        job.setCreateTime(DateUtil.today());
        job.setCreateHrId(hrId);
        job.setCompanyId(companyId);
        return jobService.save(job)?Result.succ("操作成功"):Result.fail("系统错误");
    }

    @GetMapping("/job/detail/{id}")
    @RequiresUser
    public String detail(@PathVariable("id")String id, Model model) throws Exception {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("userInfo",user);

        Job job = jobService.getById(id);
        model.addAttribute("job",job);

        Hr hr = hrService.getById(job.getCreateHrId());
        model.addAttribute("hr",hr);

        Company company = companyService.getById(job.getCompanyId());
        model.addAttribute("company",company);

        return "personal/detail_job";
    }

    @GetMapping("/job/chart")
    @RequiresRoles("admin")
    @ResponseBody
    public Map<String,Object> detail() throws Exception {
        EchartsVo jobBusiness = jobService.getNumOfJobInBusiness();
        EchartsVo jobArea = jobService.getNumOfJobInArea();
        Map<String,Object> map = new HashMap<>();
        map.put("jobBusiness",jobBusiness);
        map.put("jobArea",jobArea);
        return map;
    }

    @RequiresRoles("admin")
    @ResponseBody
    @GetMapping("/job/getByCondition")
    public TableResult<Job> getByCondition(@RequestParam("page")Long page, @RequestParam("limit")Long limit,
                                                    @RequestParam("job")String job, @RequestParam("education")String education,
                                                    @RequestParam("startDate")String startDate, @RequestParam(value = "endDate",required = false)String endDate,
                                                    @RequestParam("area")String area, @RequestParam("business")String business){
        PageResultVo<Job> pageResultVo = jobService.getByCondition(page,limit,job,education,area,business,startDate,endDate);

        return new TableResult(0,"",pageResultVo.getTotal(),pageResultVo.getRecords());
    }
}

