package com.coinhunter.web.trading.websocket;

import com.coinhunter.domain.bithumb.BithumbApiPayload;
import com.coinhunter.domain.bithumb.BithumbTicker;
import com.coinhunter.service.bithumb.BithumbApiService;
import com.coinhunter.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class TradingWebsocketController {

	private UserDetailsServiceImpl userDetailsService;

	private BithumbApiService bithumbApiService;

	private SimpMessagingTemplate template;

	@Autowired
	public TradingWebsocketController(
		UserDetailsServiceImpl userDetailsService,
		BithumbApiService bithumbApiService,
		SimpMessagingTemplate template) {
		this.userDetailsService = userDetailsService;
		this.bithumbApiService = bithumbApiService;
		this.template = template;
	}

	@MessageMapping("/trading.ticker/{userId}")
	@SendTo("/topic/trading/ticker/{userId}")
	public BithumbTicker ticker(@DestinationVariable long userId, @Payload BithumbApiPayload bithumbApiPayload) throws Exception {
		Thread.sleep(10000); // simulated delay
		BithumbTicker bithumbTicker = bithumbApiService.getTickerByCryptoCurrency(userId, bithumbApiPayload.getCryptoCurrency());
		// TODO 테스트 후 userId 제거 필요
		bithumbTicker.setUserId(bithumbApiPayload.getId());
		return bithumbTicker;
	}

}