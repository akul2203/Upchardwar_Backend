package com.upchardwar.app.entity.payload;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

	private Boolean success;
	private String message;
	private HttpStatus status;

	public ApiResponse(Boolean success, String message) {
		this.success = success;
		this.message = message;

	}
}