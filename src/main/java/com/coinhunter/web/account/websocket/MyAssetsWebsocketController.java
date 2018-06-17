package com.coinhunter.web.account.websocket;

import com.coinhunter.core.domain.bithumb.myassets.BithumbBalance;
import com.coinhunter.core.domain.bithumb.myassets.BithumbMyAssets;
import com.coinhunter.core.domain.value.CryptoCurrency;
import com.coinhunter.core.service.bithumb.BithumbApiService;
import com.coinhunter.core.service.user.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class MyAssetsWebsocketController {

	private UserDetailsServiceImpl userDetailsService;

	private BithumbApiService bithumbApiService;

	@Value("${websocket.balance.send.delay}")
	private long balanceSendDelay;

	@Autowired
	public MyAssetsWebsocketController(
		UserDetailsServiceImpl userDetailsService,
		BithumbApiService bithumbApiService) {
		this.userDetailsService = userDetailsService;
		this.bithumbApiService = bithumbApiService;
	}

	@MessageMapping("/my-assets/bithumb")
	@SendTo("/topic/my-assets/bithumb")
	public BithumbMyAssets sendMyAssets() throws Exception {
		Thread.sleep(balanceSendDelay);
		return bithumbApiService.getMyAssetsByUserId(userDetailsService.getUserId());
	}

	@MessageMapping("/my-asset/bithumb")
	@SendTo("/topic/my-asset/bithumb")
	public BithumbBalance sendMyAsset(@Payload CryptoCurrency cryptoCurrency) throws Exception {
		Thread.sleep(balanceSendDelay);
		return bithumbApiService.getBalanceByUserId(userDetailsService.getUserId(), cryptoCurrency);
	}

//	@MessageMapping("/trading.ticker/bithumb/{userId}")
//	@SendTo("/topic/trading/ticker/bithumb/{userId}")
//	public BithumbTicker ticker(@DestinationVariable long userId, @Payload BithumbApiPayload bithumbApiPayload) throws Exception {
//		Thread.sleep(tickerSendDelay);
//		return bithumbApiService.getTickerByCryptoCurrency(bithumbApiPayload.getCryptoCurrency());
//	}

}