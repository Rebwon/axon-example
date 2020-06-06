package com.rebwon.command.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rebwon.command.payload.AccountPayload;
import com.rebwon.command.payload.DepositPayload;
import com.rebwon.command.payload.HolderPayload;
import com.rebwon.command.payload.WithdrawalPayload;
import com.rebwon.command.service.TransactionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TransactionController {
	private final TransactionService transactionService;

	@PostMapping("/holder")
	public CompletableFuture<String> createHolder(@RequestBody HolderPayload holderPayload) {
		return transactionService.createHolder(holderPayload);
	}

	@PostMapping("/account")
	public CompletableFuture<String> createAccount(@RequestBody AccountPayload accountPayload){
		return transactionService.createAccount(accountPayload);
	}

	@PostMapping("/deposit")
	public CompletableFuture<String> deposit(@RequestBody DepositPayload depositPayload){
		return transactionService.depositMoney(depositPayload);
	}

	@PostMapping("/withdrawal")
	public CompletableFuture<String> withdraw(@RequestBody WithdrawalPayload withdrawalPayload){
		return transactionService.withdrawMoney(withdrawalPayload);
	}
}
