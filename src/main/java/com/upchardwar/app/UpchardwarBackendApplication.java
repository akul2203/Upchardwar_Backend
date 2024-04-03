package com.upchardwar.app;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudinary.Cloudinary;
import com.upchardwar.app.entity.Role;
import com.upchardwar.app.repository.RoleRepository;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class UpchardwarBackendApplication {

	

//    
//   @Autowired
//   private RoleRepository roleRepository;
//
//    @PostConstruct
//    public void init() {
//        // Check if the roles already exist
//        if (roleRepository.count() == 0) {
//            // Create and save default roles
//            Role role1 = new Role(1L, "ADMIN");
//            Role role2 = new Role(2L, "DOCTOR");
//            Role role3 = new Role(3L, "PATIENT");
//            Role role4 = new Role(4L, "LAB");
//            Role role5 = new Role(5L, "CHEMIST");
//            
//
//            roleRepository.save(role1);
//            roleRepository.save(role2);
//            roleRepository.save(role3); 
//            roleRepository.save(role4);
//            roleRepository.save(role5);
//            
//        }
//    }

    @Value("${cloud.name}")
	private String cloudName;
	@Value("${cloud.api-key}")
	private String cloudApiKey;
	@Value("${cloud.secret-key}")
	private String apiSecretKey;

	@Bean
	public Cloudinary cloudinary() {
		return new Cloudinary(String.format("cloudinary://%s:%s@%s",cloudApiKey,apiSecretKey,cloudName ));
	
	}
    
    
	public static void main(String[] args) {
		SpringApplication.run(UpchardwarBackendApplication.class, args);
		
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
