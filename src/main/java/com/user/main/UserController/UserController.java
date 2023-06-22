package com.user.main.UserController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.main.Dto.LoginDTO;
import com.user.main.Dto.UserDTO;
import com.user.main.Service.UserService;
import com.user.main.payload.response.LoginMessage;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	

	@PostMapping("/register")
	public String saveUser(@RequestBody UserDTO userDTO) {
		
		String id = userService.addUser(userDTO);
		return id;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
		LoginMessage loginMsg = userService.loginUser(loginDTO);
		
		return ResponseEntity.ok(loginMsg);
	}
	
	
	
}
