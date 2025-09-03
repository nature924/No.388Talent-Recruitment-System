package com.iurac.recruit.websocket;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iurac.recruit.config.WebsocketConfig;
import com.iurac.recruit.entity.ChatMessage;
import com.iurac.recruit.entity.User;
import com.iurac.recruit.service.ChatService;
import com.iurac.recruit.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/message", configurator = WebsocketConfig.class)
@Component
public class Websocket {

    //用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<Websocket> websocketSet = new CopyOnWriteArraySet<>();
    //用来记录sessionId和该session进行绑定
    private static Map<String, Session> map = new ConcurrentHashMap<>();

    private User user;

    private static ChatService chatService;
    @Autowired
    public void setChatService(ChatService chatService) {
        Websocket.chatService = chatService;
    }


    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        //获取登录时存放httpSession的用户数据
        User userInfo = (User) config.getUserProperties().get("userInfo");

        map.put(userInfo.getId(),session);
        this.user = userInfo;
        websocketSet.add(this);

        System.out.println("++++++++++++++++++++++++++++++Websocket++++++++++++++++++++++++++++++");
        System.out.println("建立连接："+user.getUsername());
        System.out.println("++++++++++++++++++++++++++++++Websocket++++++++++++++++++++++++++++++");
    }

    @OnMessage
    public void onMessage(String message){
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessage chatMessage;

        try {
            //chatMessage:{content: "...", fromUserId: "xx", toUserId: "xx"}
            chatMessage = objectMapper.readValue(message,ChatMessage.class);
            chatMessage.setId(IdUtil.simpleUUID());
            chatMessage.setSendTime(DateUtil.now());
            chatMessage.setIsLatest("1");

            //查询聊天两者的联系id
            String linkId = chatService.selectAssociation(user.getId(), chatMessage.getToUserId());
            chatMessage.setLinkId(linkId);

            //封装信息
            String sendMessage = objectMapper.writeValueAsString(MessageUtil.messageResult(chatMessage));

            //发送给发送者
            //Session fromSession = map.get(chatMessage.getFromUserId());
            //fromSession.getAsyncRemote().sendText(sendMessage);

            boolean isUnread = true;//默认对方不能直接接收，即不在线

            //发送给接收者
            Session toSession = map.get(chatMessage.getToUserId());
            //判断两者是否第一次聊天，进行聊天列表的初始化
            chatService.isFirstChat(chatMessage.getFromUserId(), chatMessage.getToUserId());
            if(ObjectUtil.isNotNull(toSession)){
                //对方在线，且在当前对话聊天的窗口则将未读置为false,因为信息一发出，对方就会立马收到
                if(chatService.isOnline(user.getId(), chatMessage.getToUserId())){
                    isUnread = false;
                }
                //对方在线，则发送信息
                toSession.getAsyncRemote().sendText(sendMessage);
            }

            //保存聊天记录信息
            chatService.saveMessage(chatMessage,isUnread);

            System.out.println("++++++++++++++++++++++++++++++Websocket++++++++++++++++++++++++++++++");
            System.out.println("收到信息："+chatMessage.getFromUserId()+"发送给"+chatMessage.getToUserId()+":"+chatMessage.getContent());
            System.out.println("++++++++++++++++++++++++++++++Websocket++++++++++++++++++++++++++++++");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(){

        System.out.println("++++++++++++++++++++++++++++++Websocket++++++++++++++++++++++++++++++");
        System.out.println("断开连接："+user.getUsername());
        System.out.println("++++++++++++++++++++++++++++++Websocket++++++++++++++++++++++++++++++");
        chatService.resetWindows(user.getId());//断开连接，重置窗口值
        map.remove(user.getId());//断开连接,提示对方自己下线
        websocketSet.remove(this);  //从set中删除
    }
}
