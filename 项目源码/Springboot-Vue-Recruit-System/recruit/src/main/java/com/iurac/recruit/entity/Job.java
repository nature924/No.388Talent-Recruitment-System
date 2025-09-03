package com.iurac.recruit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@TableName("t_job")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 岗位名
     */
    private String job;

    /**
     * 岗位类型
     */
    private String business;

    /**
     * 学历要求
     */
    private String education;

    /**
     * 工作经验要求
     */
    private String experience;

    /**
     * 工资
     */
    private String salary;

    /**
     * 工作地区
     */
    private String area;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 描述
     */
    private String description;

    /**
     * 发布时间
     */
    private String createTime;

    /**
     * HRid
     */
    private String createHrId;

    /**
     * 公司id
     */
    private String companyId;

    @TableField(exist = false)
    private String createHrName;
    @TableField(exist = false)
    private String companyName;


    public String getCreateHrName() {
        return createHrName;
    }

    public void setCreateHrName(String createHrName) {
        this.createHrName = createHrName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateHrId() {
        return createHrId;
    }

    public void setCreateHrId(String createHrId) {
        this.createHrId = createHrId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Job{" +
        "id=" + id +
        ", job=" + job +
        ", business=" + business +
        ", education=" + education +
        ", experience=" + experience +
        ", salary=" + salary +
        ", area=" + area +
        ", address=" + address +
        ", description=" + description +
        ", createtime=" + createTime +
        ", createHrId=" + createHrId +
        ", companyId=" + companyId +
        "}";
    }
}
