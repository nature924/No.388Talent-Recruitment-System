package com.iurac.recruit.vo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Comparator;
import java.util.Date;

public class ChatListVo implements Comparable<ChatListVo> {
    private String toUserId;
    private String toUserName;
    private String toUserImg;
    private String content;
    private String sendTime;

    public ChatListVo() {
    }

    public ChatListVo(String toUserId, String toUserName, String toUserImg, String content, String sendTime) {
        this.toUserId = toUserId;
        this.toUserName = toUserName;
        this.toUserImg = toUserImg;
        this.content = content;
        this.sendTime = sendTime;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getToUserImg() {
        return toUserImg;
    }

    public void setToUserImg(String toUserImg) {
        this.toUserImg = toUserImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public int compareTo(ChatListVo o) {
        if(StrUtil.hasEmpty(o.getSendTime()) || ObjectUtil.isNull(o.getSendTime())){
            return 1;
        }
        if(StrUtil.hasEmpty(this.getSendTime()) || ObjectUtil.isNull(this.getSendTime())){
            return 0;
        }
        Date date1 = DateUtil.parse(this.getSendTime());
        Date date2 = DateUtil.parse(o.getSendTime());
        return date2.compareTo(date1);
    }
}
