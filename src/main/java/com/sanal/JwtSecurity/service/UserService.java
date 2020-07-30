package com.sanal.JwtSecurity.service;

import com.sanal.JwtSecurity.model.UserModel;

public interface UserService {
	public boolean saveUser(UserModel userModel);
	
	public boolean authenticateUser(UserModel userModel);

	public String generateToken(UserModel userModel);
}
