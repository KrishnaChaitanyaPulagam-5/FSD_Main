package com.springboot.carrental.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.carrental.model.User;
import com.springboot.carrental.repository.UserRepository;
@Service
public class UserService {
	
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder= passwordEncoder;
	}

	public User registerUser(User user) {
		// TODO Auto-generated method stub
		String plainPassword=user.getPassword();
		String encodedpassword=passwordEncoder.encode(plainPassword);
		user.setPassword(encodedpassword);
		
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public Object getUserInfo(String username) {
		// TODO Auto-generated method stub
		return userRepository.getByName(username);
	}

}
