package com.coinhunter.scheduler;

import com.coinhunter.core.domain.bithumb.BithumbTicker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledUpdatesOnTopic {

	@Autowired
	private SimpMessagingTemplate template;

//	@Scheduled(fixedRate = 5000)
//	public void publishUpdates() {
//		template.convertAndSend("/topic/trading/ticker",new BithumbTicker());
//	}
}
