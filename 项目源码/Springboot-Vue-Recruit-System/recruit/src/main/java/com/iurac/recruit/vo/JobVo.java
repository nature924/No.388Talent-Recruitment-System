package com.iurac.recruit.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

public class JobVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String job;
    private String business;
    private String education;
    private String experience;
    private String salary;
    private String area;
    private String createTime;
    private String hrId;
    private String hrName;
    private String hrPosition;
    private String companyId;
    private String companyName;

    public JobVo(String id, String job, String business, String education, String experience, String salary, String area, String createTime, String hrId, String hrName, String hrPosition, String companyId, String companyName) {
        this.id = id;
        this.job = job;
        this.business = business;
        this.education = education;
        this.experience = experience;
        this.salary = salary;
        this.area = area;
        this.createTime = createTime;
        this.hrId = hrId;
        this.hrName = hrName;
        this.hrPosition = hrPosition;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public JobVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHrId() {
        return hrId;
    }

    public void setHrId(String hrId) {
        this.hrId = hrId;
    }

    public String getHrName() {
        return hrName;
    }

    public void setHrName(String hrName) {
        this.hrName = hrName;
    }

    public String getHrPosition() {
        return hrPosition;
    }

    public void setHrPosition(String hrPosition) {
        this.hrPosition = hrPosition;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
