package com.rebwon.query.service;

import org.axonframework.config.Configuration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {
	private final Configuration configuration;

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
}
