package com.iurac.recruit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 
 * @TableName t_chat_message
 */
@TableName("t_chat_message")
public class ChatMessage implements Serializable {
    /**
     * uuid
     */
    private String id;

    /**
     * 关系id
     */
    private String linkId;

    /**
     * 发送方id
     */
    private String fromUserId;

    /**
     * 接收方id
     */
    private String toUserId;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 是否为最有一条
     */
    private String isLatest;


    @TableField(exist = false)
    private String fromUserName;
    @TableField(exist = false)
    private String toUserName;

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }


    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    public String getId() {
        return id;
    }

    /**
     * uuid
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 关系id
     */
    public String getLinkId() {
        return linkId;
    }

    /**
     * 关系id
     */
    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    /**
     * 发送方id
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * 发送方id
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * 接收方id
     */
    public String getToUserId() {
        return toUserId;
    }

    /**
     * 接收方id
     */
    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    /**
     * 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 发送时间
     */
    public String getSendTime() {
        return sendTime;
    }

    /**
     * 发送时间
     */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 是否为最有一条
     */
    public String getIsLatest() {
        return isLatest;
    }

    /**
     * 是否为最有一条
     */
    public void setIsLatest(String isLatest) {
        this.isLatest = isLatest;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ChatMessage other = (ChatMessage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLinkId() == null ? other.getLinkId() == null : this.getLinkId().equals(other.getLinkId()))
            && (this.getFromUserId() == null ? other.getFromUserId() == null : this.getFromUserId().equals(other.getFromUserId()))
            && (this.getToUserId() == null ? other.getToUserId() == null : this.getToUserId().equals(other.getToUserId()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()))
            && (this.getIsLatest() == null ? other.getIsLatest() == null : this.getIsLatest().equals(other.getIsLatest()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLinkId() == null) ? 0 : getLinkId().hashCode());
        result = prime * result + ((getFromUserId() == null) ? 0 : getFromUserId().hashCode());
        result = prime * result + ((getToUserId() == null) ? 0 : getToUserId().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
        result = prime * result + ((getIsLatest() == null) ? 0 : getIsLatest().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", linkId=").append(linkId);
        sb.append(", fromUserId=").append(fromUserId);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", content=").append(content);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", isLatest=").append(isLatest);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}