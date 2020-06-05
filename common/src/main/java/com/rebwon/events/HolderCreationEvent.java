package com.rebwon.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class HolderCreationEvent {
	private String holderId;
	private String holderName;
	private String tel;
	private String addrees;
}
