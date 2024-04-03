package com.upchardwar.app.entity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialityResponse {
    private Long id;
	
	private String spName;
	
	private String spDescription;
}
