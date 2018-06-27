package com.wuxinyu.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.*;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.wuxinyu.utils.DateUtil;

@ServerEndpoint(value = "/fishChat", configurator = HttpSessionConfigurator.class)
public class FishWebSocket {
	private final ReentrantLock reentrantLock = new ReentrantLock(); //实现线程安全 防止并发  他的吞吐量要比synchronized好
	
	private static Map<String,Session> routetab = new HashMap<>();  //用户名和websocket的session绑定的路由表
	
	private static List<String> countList = new ArrayList<>();   //在线列表,记录用户名称
	
	private HttpSession httpSession;
	
	/**
	 * 用于接收传入的 WebSocket 信息，这个信息可以是文本格式，也可以是二进制格式。
	 * @param message
	 * @param session
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@OnMessage
	public void onMessage(String message, Session session) throws IOException, InterruptedException {
		System.out.println("Received: " + message);

		// 把客户端的消息解析为JSON对象
        JSONObject jsonObject = JSONObject.parseObject(message);
        // 在消息中添加发送日期
        jsonObject.put("date", DateUtil.getSystemTime());
        // 把消息发送给所有连接的会话
        for (Session openSession : session.getOpenSessions()) {
            // 添加本条消息是否为当前会话本身发的标志
            jsonObject.put("isSelf", openSession.equals(session));
            // 发送JSON格式的消息
            openSession.getAsyncRemote().sendText(jsonObject.toString());
        }
	}

	/**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		String userId = (String) httpSession.getAttribute("userId");
		addOnlineCount(userId);//连接成功添加到在线用户集合
		routetab.put(userId, session);   //将用户名和session绑定到路由表
		String message = getMessage("[" + userId + "]加入聊天室,当前在线人数为"+countList.size()+"位", "notice",  countList);
        broadcast(message, session);     //广播
	}

	@OnClose
	public void onClose(Session session) {
		String userId = (String) httpSession.getAttribute("userId");
		subOnlineCount(userId);
		String message = getMessage("[" + userId +"]离开了聊天室,当前在线人数为"+countList.size()+"位", "notice", countList);
        broadcast(message,session);       //广播
	}
	
	@OnError
	public void error(Throwable error) {
		error.printStackTrace();
	}

	/**
	 * 对特定用户发送消息
	 * 
	 * @param message
	 * @param session
	 */
	public void singleSend(String message, Session session) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	/**
	 * 组装返回给前台的消息
	 * 
	 * @param message
	 *            交互信息
	 * @param type
	 *            信息类型
	 * @param list
	 *            在线列表
	 * @return
	 */
	public String getMessage(String message, String type, List<String> list) {
		JSONObject member = new JSONObject();
		member.put("message", message);
		member.put("type", type);
		member.put("list", list);
		return member.toString();
	}
	 
	/**
	 * 广播消息
	 * 
	 * @param message
	 */
	public void broadcast(String message, Session session) {
		for (Session openSession : session.getOpenSessions()) {
			try {
				openSession.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	 
	public synchronized void addOnlineCount(String userId) {
		countList.add(userId);
	}

	public synchronized void subOnlineCount(String userId) {
		countList.remove(userId);
		routetab.remove(userId);
	}
}
