package com.rebwon.command.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.rebwon.command.commands.AccountCreationCommand;
import com.rebwon.command.commands.DepositMoneyCommand;
import com.rebwon.command.commands.WithdrawMoneyCommand;
import com.rebwon.events.AccountCreationEvent;
import com.rebwon.events.DepositMoneyEvent;
import com.rebwon.events.WithdrawMoneyEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Aggregate
public class AccountAggregate {
	@AggregateIdentifier
	private String accountId;
	private String holderId;
	private Long balance;

	@CommandHandler
	public AccountAggregate(AccountCreationCommand command) {
		apply(new AccountCreationEvent(command.getHolderId(), command.getAccountId()));
	}

	@EventSourcingHandler
	protected void createAccount(AccountCreationEvent event) {
		this.accountId = event.getAccountId();
		this.holderId = event.getHolderId();
		this.balance = 0L;
	}

	@CommandHandler
	protected void depositMoney(DepositMoneyCommand command) {
		if(command.getAmount() <= 0) throw new IllegalStateException("amount >= 0");
		apply(new DepositMoneyEvent(command.getHolderID(), command.getAccountID(), command.getAmount()));
	}

	@EventSourcingHandler
	protected void depositMoney(DepositMoneyEvent event){
		this.balance += event.getAmount();
	}

	@CommandHandler
	protected void withdrawMoney(WithdrawMoneyCommand command){
		if(this.balance - command.getAmount() < 0) throw new IllegalStateException("잔고가 부족합니다.");
		else if(command.getAmount() <= 0 ) throw new IllegalStateException("amount >= 0");
		apply(new WithdrawMoneyEvent(command.getHolderId(), command.getAccountId(), command.getAmount()));
	}

	@EventSourcingHandler
	protected void withdrawMoney(WithdrawMoneyEvent event){
		this.balance -= event.getAmount();
	}
}
