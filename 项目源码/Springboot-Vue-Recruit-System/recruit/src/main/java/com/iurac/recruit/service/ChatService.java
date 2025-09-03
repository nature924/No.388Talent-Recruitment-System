package com.iurac.recruit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iurac.recruit.entity.ChatLink;
import com.iurac.recruit.entity.ChatMessage;
import com.iurac.recruit.vo.ChatLinkVo;
import com.iurac.recruit.vo.ChatListVo;
import com.iurac.recruit.vo.PageResultVo;

import java.util.List;
import java.util.Map;

public interface ChatService {
    /**
     * 判断是否为第一次聊天，如果是则添加聊天列表表
     *
     * @param fromUserId 自身id
     * @param toUserId 对方id
     */
    void isFirstChat(String fromUserId, String toUserId);

    /**
     * 查询聊天关系表id
     *
     * @param id 自身id
     * @param toUserId 对方id
     * @return 返回聊天关系表id
     */
    String selectAssociation(String id, String toUserId);

    /**
     * 保存聊天记录：
     * 1.将上一条信息的is_latest字段设为0
     * 2.添加聊天记录
     * 3.如果对方未读，则将未读自增1
     *
     * @param chatMessage 添加的聊天记录
     * @param isUnread 对方是否未读的标识符
     */
    void saveMessage(ChatMessage chatMessage, boolean isUnread);

    /**
     * 根据用户id将所以聊天列表在线都置为0
     *
     * @param id 用户id
     */
    void resetWindows(String id);

    /**
     * 获取聊天列表vo
     *
     * @param id 用户id
     * @return 聊天列表vo，用于页面展示
     */
    List<ChatListVo> getChatListVos(String id);

    /**
     * 获取聊天历史
     *
     * @param linkId 聊天关系id
     * @return 返回当前对话的聊天记录
     */
    List<ChatMessage> getChatMessageList(String linkId);

    /**
     * 获取当前用户未读消息列表
     *
     * @param id 自身id
     * @return 一个list集合：{[id:xx,unread:xx],[]....}
     */
    List<Map<String, Object>> getUnreadById(String id);

    /**
     * 判断用户是否在当前对话的聊天窗口
     *
     * @param id 自身id
     * @param toUserId 对方id
     * @return 用户是否在当前对话的聊天窗口
     */
    boolean isOnline(String id, String toUserId);

    /**
     * 将用户对对方的未读消息数都置为0
     *
     * @param id 自身id
     * @param toUserId 对方id
     */
    void resetRead(String id, String toUserId);

    /**
     * 用户打开了对方聊天的对话窗口，则将自身上线状态置为1
     *
     * @param id 自身id
     * @param toUserId 对方id
     */
    void online(String id, String toUserId);

    /**
     * 新建一个用户关系
     *
     * @param id 自身id
     * @param toUserId 对方id
     */
    void newChat(String id, String toUserId);

    IPage<ChatLink> getChatLinkByCondition(Page<ChatLink> page, String username, String startDate, String endDate);

    IPage<ChatMessage> getChatMessageByCondition(Page<ChatMessage> page, String linkId, String usernameA, String usernameB, String content, String startDate, String endDate);

}
