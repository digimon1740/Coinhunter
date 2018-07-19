package com.coinhunter.web.transaction.websocket;

import com.coinhunter.core.domain.bithumb.BithumbApiPayload;
import com.coinhunter.core.domain.bithumb.transaction.histories.BithumbTransactionHistories;
import com.coinhunter.core.service.bithumb.BithumbApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class TransactionHistoriesController {

	private BithumbApiService bithumbApiService;

	@Value("${websocket.transaction.histories.send.delay}")
	private long historiesSendDelay;

	@Autowired
	public TransactionHistoriesController(BithumbApiService bithumbApiService) {
		this.bithumbApiService = bithumbApiService;
	}

	@MessageMapping("/transaction/histories/bithumb")
	@SendTo("/topic/transaction/histories/bithumb")
	public BithumbTransactionHistories sendTransactionHistories(@Payload BithumbApiPayload bithumbApiPayload) throws Exception {
		Thread.sleep(historiesSendDelay);
		return bithumbApiService.getTransactionHistories(bithumbApiPayload.getCryptoCurrency());
	}

}