package com.iurac.recruit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName t_chat_list
 */
@TableName("t_chat_list")
public class ChatList implements Serializable {
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
     * 发送方在线状态
     */
    private String fromOnline;

    /**
     * 接收方在线状态
     */
    private String toOnline;

    /**
     * 未读信息
     */
    private Integer unread;

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

    public String getFromOnline() {
        return fromOnline;
    }

    public void setFromOnline(String fromOnline) {
        this.fromOnline = fromOnline;
    }

    public String getToOnline() {
        return toOnline;
    }

    public void setToOnline(String toOnline) {
        this.toOnline = toOnline;
    }

    public Integer getUnread() {
        return unread;
    }

    public void setUnread(Integer unread) {
        this.unread = unread;
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
        ChatList other = (ChatList) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLinkId() == null ? other.getLinkId() == null : this.getLinkId().equals(other.getLinkId()))
            && (this.getFromUserId() == null ? other.getFromUserId() == null : this.getFromUserId().equals(other.getFromUserId()))
            && (this.getToUserId() == null ? other.getToUserId() == null : this.getToUserId().equals(other.getToUserId()))
            && (this.getFromOnline() == null ? other.getFromOnline() == null : this.getFromOnline().equals(other.getFromOnline()))
            && (this.getToOnline() == null ? other.getToOnline() == null : this.getToOnline().equals(other.getToOnline()))
            && (this.getUnread() == null ? other.getUnread() == null : this.getUnread().equals(other.getUnread()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLinkId() == null) ? 0 : getLinkId().hashCode());
        result = prime * result + ((getFromUserId() == null) ? 0 : getFromUserId().hashCode());
        result = prime * result + ((getToUserId() == null) ? 0 : getToUserId().hashCode());
        result = prime * result + ((getFromOnline() == null) ? 0 : getFromOnline().hashCode());
        result = prime * result + ((getToOnline() == null) ? 0 : getToOnline().hashCode());
        result = prime * result + ((getUnread() == null) ? 0 : getUnread().hashCode());
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
        sb.append(", fromOnline=").append(fromOnline);
        sb.append(", toOnline=").append(toOnline);
        sb.append(", unread=").append(unread);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}