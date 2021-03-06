package com.rebwon.query.service;

import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.stereotype.Service;

import com.rebwon.query.entity.HolderAccountSummary;
import com.rebwon.query.query.AccountQuery;
import com.rebwon.query.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {
	private final Configuration configuration;
	private final QueryGateway queryGateway;
	private final AccountRepository accountRepository;

	@Override
	public void reset() {
		configuration.eventProcessingConfiguration()
			.eventProcessorByProcessingGroup("accounts",
				TrackingEventProcessor.class)
			.ifPresent(trackingEventProcessor -> {
				trackingEventProcessor.shutDown();
				trackingEventProcessor.resetTokens();
				trackingEventProcessor.start();
			});
	}

	@Override
	public HolderAccountSummary getAccountInfo(String holderId) {
		AccountQuery accountQuery = new AccountQuery(holderId);
		log.debug("handling {}", accountQuery);
		return queryGateway
			.query(accountQuery, ResponseTypes.instanceOf(HolderAccountSummary.class))
			.join();
	}

	@Override
	public Flux<HolderAccountSummary> getAccountInfoSubscription(String holderId) {
		AccountQuery accountQuery = new AccountQuery(holderId);
		log.debug("handling {}", accountQuery);

		SubscriptionQueryResult<HolderAccountSummary, HolderAccountSummary> queryResult = queryGateway.subscriptionQuery(accountQuery,
			ResponseTypes.instanceOf(HolderAccountSummary.class),
			ResponseTypes.instanceOf(HolderAccountSummary.class)
		);

		return Flux.create(emitter -> {
			queryResult.initialResult().subscribe(emitter::next);
			queryResult.updates()
				.doOnNext(holder -> {
					log.debug("doOnNext : {}, isCanceled {}", holder, emitter.isCancelled());
					if (emitter.isCancelled()) {
						queryResult.close();
					}
				})
				.doOnComplete(emitter::complete)
				.subscribe(emitter::next);
		});
	}
}
