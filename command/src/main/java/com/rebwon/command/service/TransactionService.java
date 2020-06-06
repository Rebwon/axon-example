package com.rebwon.command.service;

import java.util.concurrent.CompletableFuture;

import com.rebwon.command.payload.AccountPayload;
import com.rebwon.command.payload.DepositPayload;
import com.rebwon.command.payload.HolderPayload;
import com.rebwon.command.payload.WithdrawalPayload;

public interface TransactionService {
	CompletableFuture<String> createHolder(HolderPayload holderPayload);
	CompletableFuture<String> createAccount(AccountPayload accountPayload);
	CompletableFuture<String> depositMoney(DepositPayload depositPayload);
	CompletableFuture<String> withdrawMoney(WithdrawalPayload withdrawalPayload);
}
