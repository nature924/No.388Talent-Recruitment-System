package com.iurac.recruit.util;

import com.iurac.recruit.entity.ChatMessage;

import java.util.HashMap;
import java.util.Map;

public class MessageUtil {

    public static Map<String,String> messageResult(ChatMessage chatMessage){
        Map<String,String> map = new HashMap<>();
        map.put("fromUserId",chatMessage.getFromUserId());
        map.put("content",chatMessage.getContent());
        map.put("sendTime",chatMessage.getSendTime());

        return map;

    }
}
