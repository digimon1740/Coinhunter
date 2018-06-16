package com.coinhunter.core.domain.bithumb.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BithumbApiError {

	private String status;

	private String message;
}
