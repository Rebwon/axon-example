package com.rebwon.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class WithdrawMoneyCommand {
	@TargetAggregateIdentifier
	private String accountId;
	private String holderId;
	private Long amount;
}
