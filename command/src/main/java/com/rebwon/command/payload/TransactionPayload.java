package com.rebwon.command.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPayload {
	private String accountId;
	private String holderId;
	private Long amount;
}
