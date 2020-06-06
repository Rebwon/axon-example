package com.rebwon.query.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rebwon.query.service.QueryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HolderAccountController {
	private final QueryService queryService;

	@PostMapping("/reset")
	public void reset() {
		queryService.reset();
	}
}
