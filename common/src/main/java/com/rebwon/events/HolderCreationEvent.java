package com.rebwon.events;

import org.axonframework.serialization.Revision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Revision("1.0")
public class HolderCreationEvent {
	private String holderId;
	private String holderName;
	private String tel;
	private String addrees;
	private String company;
}
