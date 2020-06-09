package com.rebwon.query.service;

import com.rebwon.query.entity.HolderAccountSummary;
import reactor.core.publisher.Flux;

public interface QueryService {
	void reset();
	HolderAccountSummary getAccountInfo(String holderId);
	Flux<HolderAccountSummary> getAccountInfoSubscription(String holderId);
}
