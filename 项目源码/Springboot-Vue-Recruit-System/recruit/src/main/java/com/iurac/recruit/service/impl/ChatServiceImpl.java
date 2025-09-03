package com.iurac.recruit.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iurac.recruit.entity.ChatLink;
import com.iurac.recruit.entity.ChatList;
import com.iurac.recruit.entity.ChatMessage;
import com.iurac.recruit.entity.User;
import com.iurac.recruit.mapper.ChatLinkMapper;
import com.iurac.recruit.mapper.ChatListMapper;
import com.iurac.recruit.mapper.ChatMessageMapper;
import com.iurac.recruit.mapper.UserMapper;
import com.iurac.recruit.service.ChatService;
import com.iurac.recruit.vo.ChatLinkVo;
import com.iurac.recruit.vo.ChatListVo;
import com.iurac.recruit.vo.ChatMessageVo;
import com.iurac.recruit.vo.PageResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("chatService")
@Transactional
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatLinkMapper chatLinkMapper;
    @Autowired
    private ChatListMapper chatListMapper;
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void isFirstChat(String fromUserId, String toUserId) {
        String linkId = selectAssociation(fromUserId,toUserId);

        QueryWrapper<ChatList> chatListQueryWrapper = new QueryWrapper<>();
        chatListQueryWrapper.eq("link_id",linkId).eq("from_user_id",fromUserId).eq("to_user_id",toUserId);
        ChatList chatList = chatListMapper.selectOne(chatListQueryWrapper);

        if(ObjectUtil.isNull(chatList)){
            chatList = new ChatList();
            chatList.setId(IdUtil.simpleUUID());
            chatList.setLinkId(linkId);
            chatList.setFromUserId(fromUserId);
            chatList.setToUserId(toUserId);
            chatList.setFromOnline("1");
            chatList.setToOnline("0");
            chatList.setUnread(0);
            chatListMapper.insert(chatList);
        }
    }

    @Override
    public String selectAssociation(String id, String toUserId) {
        QueryWrapper<ChatLink> linkQueryWrapper = new QueryWrapper<>();
        linkQueryWrapper.or(wrapper -> wrapper.eq("to_user_id", id).eq("from_user_id", toUserId));
        linkQueryWrapper.or(wrapper -> wrapper.eq("to_user_id", toUserId).eq("from_user_id", id));

        ChatLink chatLink = chatLinkMapper.selectOne(linkQueryWrapper);

        return ObjectUtil.isNull(chatLink)?null:chatLink.getId();
    }

    @Override
    public void saveMessage(ChatMessage chatMessage, boolean isUnread) {
        UpdateWrapper<ChatMessage> messageUpdateWrapper = new UpdateWrapper<>();
        messageUpdateWrapper.eq("link_id",chatMessage.getLinkId()).eq("is_latest","1");
        messageUpdateWrapper.set("is_latest","0");
        chatMessageMapper.update(null,messageUpdateWrapper);

        chatMessageMapper.insert(chatMessage);

        if(isUnread){
            QueryWrapper<ChatList> listQueryWrapper = new QueryWrapper<>();
            listQueryWrapper.eq("from_user_id",chatMessage.getFromUserId())
                    .eq("to_user_id",chatMessage.getToUserId());
            ChatList chatList = chatListMapper.selectOne(listQueryWrapper);
            Integer unread = chatList.getUnread()+1;
            chatList.setUnread(unread);
            chatListMapper.update(chatList,listQueryWrapper);
        }

    }

    @Override
    public void resetWindows(String id) {
        UpdateWrapper<ChatList> listUpdateWrapper1 = new UpdateWrapper<>();
        listUpdateWrapper1.eq("from_user_id",id);
        listUpdateWrapper1.set("from_online","0");
        chatListMapper.update(null,listUpdateWrapper1);

        UpdateWrapper<ChatList> listUpdateWrapper2 = new UpdateWrapper<>();
        listUpdateWrapper2.eq("to_user_id",id);
        listUpdateWrapper2.set("to_online","0");
        chatListMapper.update(null,listUpdateWrapper2);

    }

    @Override
    public List<ChatListVo> getChatListVos(String id) {
        QueryWrapper<ChatLink> linkQueryWrapper = new QueryWrapper<>();
        linkQueryWrapper.eq("to_user_id", id).or().eq("from_user_id", id);
        List<ChatLink> chatLinkList = chatLinkMapper.selectList(linkQueryWrapper);

        List<ChatListVo> chatListVos = new ArrayList<>();
        chatLinkList.forEach(chatLink ->{
            ChatListVo chatListVo = new ChatListVo();
            QueryWrapper<ChatMessage> messageQueryWrapper = new QueryWrapper<>();
            messageQueryWrapper.eq("link_id",chatLink.getId()).eq("is_latest","1");
            ChatMessage chatMessage = chatMessageMapper.selectOne(messageQueryWrapper);

            if(ObjectUtil.isNotNull(chatMessage)){
                chatListVo.setSendTime(chatMessage.getSendTime());
                chatListVo.setContent(chatMessage.getContent());
            }

            String toUserId = id.equals(chatLink.getFromUserId())?chatLink.getToUserId():chatLink.getFromUserId();
            User user = userMapper.selectById(toUserId);
            chatListVo.setToUserId(user.getId());
            chatListVo.setToUserName(user.getUsername());
            chatListVo.setToUserImg(user.getImg());
            chatListVos.add(chatListVo);
        });
        Collections.sort(chatListVos);
        return chatListVos;
    }


    @Override
    public List<ChatMessage> getChatMessageList(String linkId) {
        QueryWrapper<ChatMessage> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("link_id",linkId).orderByAsc("send_time");
        return chatMessageMapper.selectList(messageQueryWrapper);
    }

    @Override
    public List<Map<String, Object>> getUnreadById(String id) {
        return chatListMapper.getUnreadById(id);
    }

    @Override
    public boolean isOnline(String id, String toUserId) {
        QueryWrapper<ChatList> listQueryWrapper = new QueryWrapper<>();
        listQueryWrapper.eq("from_user_id",id).eq("to_user_id",toUserId);
        ChatList chatList = chatListMapper.selectOne(listQueryWrapper);
        if("1".equals(chatList.getToOnline()) && toUserId.equals(chatList.getToUserId())){
            return true;
        }
        return false;
    }

    @Override
    public void resetRead(String id, String toUserId) {
        UpdateWrapper<ChatList> listUpdateWrapper = new UpdateWrapper<>();
        listUpdateWrapper.eq("from_user_id",toUserId).eq("to_user_id",id);
        listUpdateWrapper.set("unread",0);
        chatListMapper.update(null,listUpdateWrapper);
    }

    @Override
    public void online(String id, String toUserId) {
        UpdateWrapper<ChatList> listUpdateWrapper1 = new UpdateWrapper<>();
        listUpdateWrapper1.eq("from_user_id",toUserId).eq("to_user_id",id);
        listUpdateWrapper1.set("to_online","1");
        chatListMapper.update(null,listUpdateWrapper1);

        UpdateWrapper<ChatList> listUpdateWrapper2 = new UpdateWrapper<>();
        listUpdateWrapper1.eq("from_user_id",id).eq("to_user_id",toUserId);
        listUpdateWrapper2.set("from_online","1");
        chatListMapper.update(null,listUpdateWrapper2);
    }

    @Override
    public void newChat(String id, String toUserId) {
        ChatLink chatLink = new ChatLink();
        chatLink.setId(IdUtil.simpleUUID());
        chatLink.setFromUserId(id);
        chatLink.setToUserId(toUserId);
        chatLink.setCreateTime(DateUtil.now());

        chatLinkMapper.insert(chatLink);
    }

    @Override
    public IPage<ChatLink> getChatLinkByCondition(Page<ChatLink> page, String username, String startDate, String endDate) {
        return chatLinkMapper.getChatLinkByCondition(page,username,startDate,endDate);
    }

    @Override
    public IPage<ChatMessage> getChatMessageByCondition(Page<ChatMessage> page, String linkId, String usernameA, String usernameB, String content, String startDate, String endDate) {
        return chatMessageMapper.getChatMessageByCondition(page,linkId,usernameA,usernameB,content,startDate,endDate);
    }
}
