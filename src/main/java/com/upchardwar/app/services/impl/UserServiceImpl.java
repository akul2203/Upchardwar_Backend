package com.upchardwar.app.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.upchardwar.app.entity.Role;
import com.upchardwar.app.entity.User;
import com.upchardwar.app.entity.UserRole;
import com.upchardwar.app.entity.payload.ChangePasswordRequest;
import com.upchardwar.app.entity.payload.UserRequest;
import com.upchardwar.app.entity.payload.UserResponse;
import com.upchardwar.app.entity.status.AppConstant;
import com.upchardwar.app.exception.ResourceAlreadyExistException;
import com.upchardwar.app.exception.ResourceNotFoundException;
import com.upchardwar.app.repository.DoctorRepository;
import com.upchardwar.app.repository.LabRepository;
import com.upchardwar.app.repository.PharmaRepository;
import com.upchardwar.app.repository.RoleRepository;
import com.upchardwar.app.repository.UserRepository;
import com.upchardwar.app.services.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository urepo;

	@Autowired
	private RoleRepository rrepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private LabRepository labRepository;

    @Autowired
    private PharmaRepository pharmacyRepository;

    
	public UserResponse userToUserResponse(User user) {
		return this.modelMapper.map(user, UserResponse.class);
	}

	public User userRequestToUser(UserRequest userRequest) {
		return this.modelMapper.map(userRequest, User.class);
	}

	@Override
	public UserResponse createUser(UserRequest request) {

		Optional<User> local = this.urepo.findByEmail(request.getEmail());
		if (local.isPresent())
			throw new ResourceAlreadyExistException(AppConstant.USER_EMAIL_ALREADY_EXIST);

//			for(UserRole ur:userRoles) {
//				rrepo.save(ur.getRole());
//			}

		// request.getUserRole().addAll(userRoles);

		User user = this.userRequestToUser(request);

		System.err.println("...");
		Set<UserRole> roles = new HashSet();
		Role role = new Role();
		role.setRoleId(1L);
		UserRole userRole = new UserRole();
		userRole.setRole(role);
		userRole.setUser(user);
		roles.add(userRole);
		user.setUserRole(roles);

		return this.userToUserResponse(this.urepo.save(user));

	}
	
	
	

	public ResponseEntity<?> changeStatus(Long id, String status) {
		Map<String, Object> response = new HashMap<>();

		Optional<User> local = this.urepo.findByIdAndStatus(id, AppConstant.USER_STATUS_NOT_VARIFIED);
		if (local.isPresent()) {
			User u = local.get();
			u.setStatus(status);
		} else {
			throw new ResourceNotFoundException(AppConstant.USER_NOT_FOUND);
		}
		response.put("Status", AppConstant.USER_STATUS_CHANGED_SUCCESSFULLY);
		return new ResponseEntity(response, HttpStatus.OK);
	}

	@Override
	public void deleteUser(UserRequest request) {
		// TODO Auto-generated method stub

	}


	public UserResponse getUserByName(String name) {

		Optional<User> local = this.urepo.findByEmail(name);
		if (local.isPresent())
			return this.userToUserResponse(local.get());

		else
			throw new ResourceNotFoundException(AppConstant.USER_NOT_FOUND);
	}

    public Map<String, Object> search(String searchTerm) {
    	Map<String, Object> response = new HashMap<>();
        List<Object> results = new ArrayList<>(); 

    
        response.put("doctor",doctorRepository.searchDoctors(searchTerm) );
        response.put("lab",labRepository.searchLabs(searchTerm) );
        response.put("pharmacy",pharmacyRepository.searchPharmacies(searchTerm) );
        return response;
    }

	@Override
	public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOptional = urepo.findByEmail(changePasswordRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
                if (!passwordEncoder.matches(changePasswordRequest.getNewPassword(), user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
                    urepo.save(user);
                    response.put(AppConstant.MESSAGE, AppConstant.CHANGE_PASSWORD_SUCCESS);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.put(AppConstant.MESSAGE, AppConstant.NEW_PASSWORD_SAME_AS_OLD);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            } else {
                response.put(AppConstant.MESSAGE, AppConstant.PASSWORD_NOT_MATCHED);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        response.put(AppConstant.MESSAGE, AppConstant.USER_NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

	@Override
	public ResponseEntity<?> getallusers() {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		response.put("users",this.urepo.findAll() );
		response.put("message","Users Founds");
		if(Objects.nonNull(response))
		return new ResponseEntity<>(response,HttpStatus.OK);
		return null;
	}

	@Override
	public List<User> getUsersByEmails(List<String> emails) {
        return urepo.findByEmailIn(emails);
    }

	@Override
	public Optional<User> getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return this.urepo.findByEmail(email);
	}

}
