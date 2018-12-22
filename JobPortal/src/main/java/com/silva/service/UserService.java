package com.silva.service;

import com.silva.model.User;

public interface UserService {
	
	public User findUserByEmail(String email);
	 
	public void saveUser(User user);
	
	public void saveEmployerUser(User user);
	
}
