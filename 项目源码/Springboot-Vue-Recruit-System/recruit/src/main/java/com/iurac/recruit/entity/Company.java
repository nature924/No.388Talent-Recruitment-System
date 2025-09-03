package com.iurac.recruit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author iurac
 * @since 2021-06-03
 */
@TableName("t_company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 图标
     */
    private String img;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 上市状态
     */
    private String listedStatus;

    /**
     * 注册时间
     */
    private String registrationTime;

    /**
     * 人数规模
     */
    private String size;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getListedStatus() {
        return listedStatus;
    }

    public void setListedStatus(String listedStatus) {
        this.listedStatus = listedStatus;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Company{" +
        "id=" + id +
        ", name=" + name +
        ", img=" + img +
        ", introduction=" + introduction +
        ", listedstatus=" + listedStatus +
        ", registrationTime=" + registrationTime +
        ", size=" + size +
        "}";
    }
}
