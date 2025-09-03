package com.iurac.recruit.vo;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

public class ChatMessageVo implements Serializable {

    private String id;
    private String linkId;
    private String fromUserId;
    private String fromUserName;
    private String fromUserImg;
    private String toUserId;
    private String toUserName;
    private String toUserImg;
    private String content;
    private String sendTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
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


}