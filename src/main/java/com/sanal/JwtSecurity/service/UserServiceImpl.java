package com.sanal.JwtSecurity.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanal.JwtSecurity.model.UserModel;
import com.sanal.JwtSecurity.repo.UserRepo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService {

	private static final String SECURITYKEY = "securitykey";
	
	private static final int EXPIRY_TIME_IN_MILLIS = 10000000;
	@Autowired
	private UserRepo userrepo;

	@Override
	public boolean saveUser(UserModel userModel) {
		try {
			userrepo.save(userModel);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean authenticateUser(UserModel userModel) {
		UserModel userFromDB = null;
		try {
			userFromDB = userrepo.findByUsername(userModel.getUsername());
		} catch (Exception e) {
			return false;
		}
		if (userFromDB == null) {
			return false;
		} else if (userFromDB.getUsername().equals(userModel.getUsername()) 
				&& userFromDB.getPassword().equals(userModel.getPassword())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String generateToken(UserModel userModel) {
		return Jwts.builder().setSubject(userModel.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRY_TIME_IN_MILLIS))
				.signWith(SignatureAlgorithm.HS256, SECURITYKEY)
				.compact();
	}

}
