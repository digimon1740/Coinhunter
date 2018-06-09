package com.coinhunter.web.listener;

import com.coinhunter.domain.chat.ChatMessage;
import com.coinhunter.domain.user.User;
import com.coinhunter.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;

@Slf4j
@Component
public class WebSocketEventListener {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@Autowired
	private UserService userService;

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		log.info("Received a new web socket connection");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		log.info("headerAccessor : {}", headerAccessor);
		//String username = (String) headerAccessor.getSessionAttributes().get("username");
		String username = headerAccessor.getUser().getName();
		if (username != null) {
			log.info("User Disconnected : " + username);
			User user = userService.findByName(username);

			// 모든 웹소켓 처리 close 예) 채팅, ticker등 스레드 api, 자동봇?(이건 생각해봐야함)

//			ChatMessage chatMessage = new ChatMessage();
//			chatMessage.setType(ChatMessage.MessageType.LEAVE);
//			chatMessage.setSender(username);

			//messagingTemplate.convertAndSend("/topic/public", chatMessage);
		}
	}
}
