package com.user.main.Service.Impl;

import java.util.Optional;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.main.Dto.LoginDTO;
import com.user.main.Dto.UserDTO;
import com.user.main.Entity.User;
import com.user.main.Repo.UserRepo;
import com.user.main.payload.response.LoginMessage;
import com.user.main.Service.UserService;

@Service
public class UserImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String addUser(UserDTO userDTO) {
		try {
			
			if(userDTO.getName().isEmpty() || userDTO.getEmail().isEmpty() || userDTO.getPassword().isEmpty()) {
				return "All fields required!";
			}
			else if(userDTO.getPassword().length() < 8) {
				return "Password should be at least 8 characters!";
			}
			
			User user = new User(
					
					userDTO.getId(),
					userDTO.getName(),
					userDTO.getEmail(),
					this.passwordEncoder.encode(userDTO.getPassword())
			);
			
			userRepo.save(user);
			
			return "Registration Successful!";
			//return user;
		}
		catch(Exception e) {
			return "Error Occured!";
		}
		
		
		
		
	}

	@Override
	public LoginMessage loginUser(LoginDTO loginDTO) {
		String msg = "";
		User user1 = userRepo.findByEmail(loginDTO.getEmail());
		
		if(user1!=null) {
			String password = loginDTO.getPassword();
			String encodedPassword = user1.getPassword();
			Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
			if(isPwdRight) {
				Optional<User> user = userRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
				if(user.isPresent()) {
					return new LoginMessage("Login Success", true);
				}
				else {
					return new LoginMessage("Login Failed", false);
				}
			}
			else {
				return new LoginMessage("Password did not match", false);
			}
		}
		else {
			return new LoginMessage("Email does not exist", false);
		}
	}

}
