package com.coinhunter.core.domain.bithumb.myassets;

import com.coinhunter.core.domain.value.CryptoCurrency;
import lombok.*;
import org.apache.commons.collections.MapUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * status	결과 상태 코드 (정상 : 0000, 정상이외 코드는 에러 코드 참조)
 * total_{currency}	전체 Currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum, btg, eos, icx, ven, trx, elf, mith, mco, omg, knc, gnt, hsr, zil, ethos, pay, wax, powr, lrc, gto, steem, strat, zrx, rep, ae, xem, snt, ada)
 * total_krw	전체 KRW
 * in_use_{currency}	사용중 Currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum, btg, eos, icx, ven, trx, elf, mith, mco, omg, knc, gnt, hsr, zil, ethos, pay, wax, powr, lrc, gto, steem, strat, zrx, rep, ae, xem, snt, ada)
 * in_use_krw	사용중 KRW
 * available_{currency}	사용 가능 Currency (btc, eth, dash, ltc, etc, xrp, bch, xmr, zec, qtum, btg, eos, icx, ven, trx, elf, mith, mco, omg, knc, gnt, hsr, zil, ethos, pay, wax, powr, lrc, gto, steem, strat, zrx, rep, ae, xem, snt, ada)
 * available_krw	사용 가능 KRW
 * xcoin_last	bithumb 마지막 거래체결 금액
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BithumbBalance {

	private String status;

	private CryptoCurrency cryptoCurrency;

	private Map<String, Object> data = new HashMap<>();

	private BithumbBalance() {
	}

	public long getTotalKrw() {
		return MapUtils.getLongValue(data, "total_krw", 0);
	}

	public long getAvailableKrw() {
		return MapUtils.getLongValue(data, "available_krw", 0);
	}

	public long getUsingKrw() {
		return MapUtils.getLongValue(data, "in_use_krw", 0);
	}

	public boolean isSuccess() {
		return "0000".equals(status);
	}
}