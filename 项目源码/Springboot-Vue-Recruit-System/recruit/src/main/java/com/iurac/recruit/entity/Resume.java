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
@TableName("t_resume")
public class Resume implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生日期
     */
    private String birth;

    /**
     * 年龄
     */
    private String age;

    /**
     * 照片
     */
    private String photo;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 婚姻状态
     */
    private String marriage;

    /**
     * 政治面貌
     */
    private String political;

    /**
     * 期望薪资
     */
    private String salary;

    /**
     * 应聘岗位
     */
    private String job;

    /**
     * 毕业学校
     */
    private String college;

    /**
     * 专业
     */
    private String major;

    /**
     * 学历
     */
    private String education;

    /**
     * 工作经历
     */
    private String experience;

    /**
     * 技术能力
     */
    private String technology;

    /**
     * 自我介绍
     */
    private String introduction;

    @TableField(exist = false)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getPolitical() {
        return political;
    }

    public void setPolitical(String political) {
        this.political = political;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
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

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Override
    public String toString() {
        return "Resume{" +
        "id=" + id +
        ", userId=" + userId +
        ", name=" + name +
        ", sex=" + sex +
        ", birth=" + birth +
        ", age=" + age +
        ", photo=" + photo +
        ", phone=" + phone +
        ", email=" + email +
        ", marriage=" + marriage +
        ", political=" + political +
        ", salary=" + salary +
        ", job=" + job +
        ", college=" + college +
        ", major=" + major +
        ", education=" + education +
        ", experience=" + experience +
        ", technology=" + technology +
        ", introduction=" + introduction +
        "}";
    }
}
