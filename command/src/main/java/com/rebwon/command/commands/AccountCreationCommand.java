package com.rebwon.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class AccountCreationCommand {
	@TargetAggregateIdentifier
	private String holderId;
	private String accountId;
}
