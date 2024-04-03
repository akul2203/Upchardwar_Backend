package com.upchardwar.app.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomExceptionMessage {
	
	private String Date;
	private int Code;
	private String status;
	private String msg;
}
