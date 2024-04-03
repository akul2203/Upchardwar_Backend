package com.upchardwar.app.entity.payload;

import java.util.HashSet;
import java.util.Set;

import com.upchardwar.app.entity.Role;
import com.upchardwar.app.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
	private Long id;
    private String name;
    private String email;
    private String password;
    private String status;
    private Role role;
    private  Set<UserRole> userRole=new HashSet<>();
  
}
