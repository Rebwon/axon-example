package com.rebwon.command.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HolderPayload {
	private String holderName;
	private String tel;
	private String address;
}
