package com.upchardwar.app.controller.doctor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upchardwar.app.entity.User;
import com.upchardwar.app.entity.payload.UserRequest;
import com.upchardwar.app.entity.payload.UserResponse;
import com.upchardwar.app.services.IUserService;

@RestController
@RequestMapping("/upchardwar/user")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private IUserService service;

	@PostMapping("/")
	public String createUser(@RequestBody UserRequest user) throws Exception {

		UserResponse u = service.createUser(user);
		return null;
	}

	@GetMapping("/search")
	public ResponseEntity<Map<String, Object>> search(@RequestParam String searchTerm) {
		return ResponseEntity.ok(service.search(searchTerm));
	}

	@GetMapping("/getall")
	public ResponseEntity<?> getallusers() {
		return ResponseEntity.ok(service.getallusers());
	}

    @GetMapping("/users-by-emails")
    public List<User> getUsersByEmails(@RequestParam List<String> emails) {
        return service.getUsersByEmails(emails);
    }
    
    @GetMapping("/users-by-email/{email}")
    public Optional<User> getUsersByEmails(@PathVariable String email) {
        return service.getUserByEmail(email);
    }
	
}