package com.upchardwar.app.entity.lab;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabTransaction {

	private String orderId;
	private String currency;
	private Integer amount;
	
}
