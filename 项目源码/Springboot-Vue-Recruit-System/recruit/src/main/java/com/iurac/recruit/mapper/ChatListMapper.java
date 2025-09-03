package com.iurac.recruit.mapper;

import com.iurac.recruit.entity.ChatList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @Entity com.iurac.recruit.entity.ChatList
 */
public interface ChatListMapper extends BaseMapper<ChatList> {
    List<Map<String, Object>> getUnreadById(String id);
}




