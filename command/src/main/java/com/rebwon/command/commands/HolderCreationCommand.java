package com.rebwon.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class HolderCreationCommand {
	@TargetAggregateIdentifier
	private String holderId;
	private String holderName;
	private String tel;
	private String address;
	private String company;
}
