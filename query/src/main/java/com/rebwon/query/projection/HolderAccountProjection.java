package com.rebwon.query.projection;

import java.time.Instant;
import java.util.NoSuchElementException;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import com.rebwon.events.AccountCreationEvent;
import com.rebwon.events.DepositMoneyEvent;
import com.rebwon.events.HolderCreationEvent;
import com.rebwon.events.WithdrawMoneyEvent;
import com.rebwon.query.entity.HolderAccountSummary;
import com.rebwon.query.query.AccountQuery;
import com.rebwon.query.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableRetry
@AllArgsConstructor
@ProcessingGroup("accounts")
public class HolderAccountProjection {
	private final AccountRepository repository;
	private final QueryUpdateEmitter queryUpdateEmitter;

	@EventHandler
	@AllowReplay
	protected void on(HolderCreationEvent event, @Timestamp Instant instant) {
		log.debug("projecting {} , timestamp : {}", event, instant.toString());
		HolderAccountSummary accountSummary = HolderAccountSummary.builder()
			.holderId(event.getHolderId())
			.name(event.getHolderName())
			.address(event.getAddrees())
			.tel(event.getTel())
			.totalBalance(0L)
			.accountCnt(0L)
			.build();
		repository.save(accountSummary);
	}

	@EventHandler
	@AllowReplay
	@Retryable(value = {NoSuchElementException.class}, maxAttempts = 5, backoff = @Backoff(delay = 1000))
	protected void on(AccountCreationEvent event, @Timestamp Instant instant){
		log.debug("projecting {} , timestamp : {}", event, instant.toString());
		HolderAccountSummary holderAccount = getHolderAccountSummary(event.getHolderId());
		holderAccount.setAccountCnt(holderAccount.getAccountCnt()+1);
		repository.save(holderAccount);
	}

	@EventHandler
	@AllowReplay
	protected void on(DepositMoneyEvent event, @Timestamp Instant instant){
		log.debug("projecting {} , timestamp : {}", event, instant.toString());
		HolderAccountSummary holderAccount = getHolderAccountSummary(event.getHolderId());
		holderAccount.setTotalBalance(holderAccount.getTotalBalance() + event.getAmount());

		queryUpdateEmitter.emit(AccountQuery.class,
			query -> query.getHolderId().equals(event.getHolderId()),
			holderAccount);

		repository.save(holderAccount);
	}

	@EventHandler
	@AllowReplay
	protected void on(WithdrawMoneyEvent event, @Timestamp Instant instant){
		log.debug("projecting {} , timestamp : {}", event, instant.toString());
		HolderAccountSummary holderAccount = getHolderAccountSummary(event.getHolderID());
		holderAccount.setTotalBalance(holderAccount.getTotalBalance() - event.getAmount());

		queryUpdateEmitter.emit(AccountQuery.class,
			query -> query.getHolderId().equals(event.getHolderID()),
			holderAccount);

		repository.save(holderAccount);
	}

	private HolderAccountSummary getHolderAccountSummary(String holderId) {
		log.debug("getHolder : {} ", holderId);
		return repository.findByHolderId(holderId)
			.orElseThrow(() -> new NoSuchElementException("소유주가 존재하지 않습니다."));
	}

	@ResetHandler
	private void resetHolderAccountInfo() {
		log.debug("reset triggered");
		repository.deleteAll();
	}

	@QueryHandler
	public HolderAccountSummary on(AccountQuery query) {
		log.debug("handling {}", query);
		return repository.findByHolderId(query.getHolderId()).orElse(null);
	}
}
