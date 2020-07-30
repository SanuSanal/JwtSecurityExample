package com.sanal.JwtSecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sanal.JwtSecurity.model.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {
	
	public UserModel findByUsername(String username);
}
