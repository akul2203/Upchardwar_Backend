package com.upchardwar.app.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.upchardwar.app.entity.status.AppConstant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Varification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String otp;
	
	@Column(unique = true)
	private String email;
	
//	 @OneToOne( fetch = FetchType.EAGER)
//	    @JoinColumn(nullable = false, name = "user_id")
//	private User user;
//	
	private LocalDateTime exprireTime;
	
	private Boolean isActive=false;
	
	private Long roleId;
	
	private String password;
	
	
}
