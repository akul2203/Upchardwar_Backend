package com.upchardwar.app.entity.payload;

import com.upchardwar.app.entity.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PharmaResponse {
    private Long id;
	
	private String pharmaName;
	
	private String email;
	
	private Boolean isApproved=false;
	
	private String password;
	
	private String phone;
	
	private Location location;
}
