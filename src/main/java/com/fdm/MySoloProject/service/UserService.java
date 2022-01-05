package com.fdm.MySoloProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.MySoloProject.dal.UserRepository;
import com.fdm.MySoloProject.model.User;

@Service
public class UserService {
	private UserRepository userRepo;

	
	@Autowired
	public UserService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	

	
	public User save(User toBeProcessed) {
		if (userRepo.countByUsername(toBeProcessed.getUsername()) == 0) {
			return userRepo.save(toBeProcessed);
		} else {
			return null;
		}
	}
	
	public void remove(User toBeRemoved) {
		userRepo.delete(toBeRemoved);
	}
	
	
	public List<User> findAllUsers(){
		return userRepo.findAll();
	}
	
	public User login(User toBeLoggedIn) {
		Optional<User> foundUser = userRepo.findByUsernameAndPassword(toBeLoggedIn.getUsername(), toBeLoggedIn.getPassword());
		if (foundUser.isPresent()) {
			return foundUser.get();
		} else {
			return null;
		}
	}
	

	
	public User findById(int UserId) {
		Optional<User> foundUser = userRepo.findById(UserId);
		if(foundUser.isEmpty()) {
			return null;
		}
		return foundUser.get();
	}
	
	
}

