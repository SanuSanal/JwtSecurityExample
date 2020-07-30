/**
 * 
 */
package com.sanal.JwtSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanal.JwtSecurity.model.UserModel;
import com.sanal.JwtSecurity.service.UserService;

/**
 * @author admin
 *
 */
@RestController
@RequestMapping("security")
public class HomeController {
	
	@Autowired
	private UserService userservice;
	
	@GetMapping("/api/v1/home")
	public ResponseEntity<String> rootAction() {
		return new ResponseEntity<String>("home page", HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUpUser(@RequestBody UserModel userModel) {
		if (userModel != null && userModel.getUsername() != null && !userModel.getUsername().isEmpty()
				&& userModel.getPassword() != null && !userModel.getPassword().isEmpty()) {
			boolean status = userservice.saveUser(userModel);
			if (status) {
				return new ResponseEntity<UserModel>(userModel, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("sign-up failed!!", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<String>("please enter all data!!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserModel userModel) {
		if (userModel != null && userModel.getUsername() != null && !userModel.getUsername().isEmpty()
				&& userModel.getPassword() != null && !userModel.getPassword().isEmpty()) {
			boolean status = userservice.authenticateUser(userModel);
			if (status) {
				String jwt = userservice.generateToken(userModel);
				return new ResponseEntity<String>(jwt, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("login failed", HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<String>("No sufficient data", HttpStatus.BAD_REQUEST);
		}
	}
	
}
