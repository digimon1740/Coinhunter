package com.coinhunter.web.ticker.websocket;

import com.coinhunter.core.domain.bithumb.BithumbApiPayload;
import com.coinhunter.core.domain.bithumb.ticker.BithumbTicker;
import com.coinhunter.core.service.bithumb.BithumbApiService;
import com.coinhunter.core.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class TickerWebsocketController {

	private UserDetailsServiceImpl userDetailsService;

	private BithumbApiService bithumbApiService;

	private SimpMessagingTemplate template;

	@Value("${websocket.ticker.send.delay}")
	private long tickerSendDelay;

	@Autowired
	public TickerWebsocketController(
		UserDetailsServiceImpl userDetailsService,
		BithumbApiService bithumbApiService,
		SimpMessagingTemplate template) {
		this.userDetailsService = userDetailsService;
		this.bithumbApiService = bithumbApiService;
		this.template = template;
	}

	@MessageMapping("/ticker/bithumb")
	@SendTo("/topic/ticker/bithumb")
	public BithumbTicker sendTicker(@Payload BithumbApiPayload bithumbApiPayload) throws Exception {
		Thread.sleep(tickerSendDelay);
		return bithumbApiService.getTickerByCryptoCurrency(bithumbApiPayload.getCryptoCurrency());
	}

}