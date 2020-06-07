package com.rebwon.command.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.rebwon.command.commands.AccountCreationCommand;
import com.rebwon.command.commands.DepositMoneyCommand;
import com.rebwon.command.commands.HolderCreationCommand;
import com.rebwon.command.commands.WithdrawMoneyCommand;
import com.rebwon.command.payload.AccountPayload;
import com.rebwon.command.payload.DepositPayload;
import com.rebwon.command.payload.HolderPayload;
import com.rebwon.command.payload.WithdrawalPayload;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
	private final CommandGateway commandGateway;

	@Override
	public CompletableFuture<String> createHolder(HolderPayload holderPayload) {
		return commandGateway.send(new HolderCreationCommand(UUID.randomUUID().toString(),
			holderPayload.getHolderName(),
			holderPayload.getTel(),
			holderPayload.getAddress(),
			holderPayload.getCompany()));
	}

	@Override
	public CompletableFuture<String> createAccount(AccountPayload accountPayload) {
		return commandGateway.send(new AccountCreationCommand(accountPayload.getHolderId(),UUID.randomUUID().toString()));
	}

	@Override
	public CompletableFuture<String> depositMoney(DepositPayload depositPayload) {
		return commandGateway.send(new DepositMoneyCommand(depositPayload.getAccountId(), depositPayload.getHolderId(), depositPayload.getAmount()));
	}

	@Override
	public CompletableFuture<String> withdrawMoney(WithdrawalPayload withdrawalPayload) {
		return commandGateway.send(new WithdrawMoneyCommand(withdrawalPayload.getAccountId(), withdrawalPayload.getHolderId(), withdrawalPayload.getAmount()));
	}
}
