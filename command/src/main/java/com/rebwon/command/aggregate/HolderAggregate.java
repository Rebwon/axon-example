package com.rebwon.command.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.rebwon.command.commands.HolderCreationCommand;
import com.rebwon.events.HolderCreationEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Aggregate
public class HolderAggregate {
	@AggregateIdentifier
	private String holderID;
	private String holderName;
	private String tel;
	private String address;

	@CommandHandler
	public HolderAggregate(HolderCreationCommand command) {
		apply(new HolderCreationEvent(command.getHolderId(), command.getHolderName(), command.getTel(), command.getAddress()));
	}

	@EventSourcingHandler
	protected void createAccount(HolderCreationEvent event) {
		this.holderID = event.getHolderId();
		this.holderName = event.getHolderName();
		this.tel = event.getTel();
		this.address = event.getAddrees();
	}
}
