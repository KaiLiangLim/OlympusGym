package com.fdm.MySoloProject.dal;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fdm.MySoloProject.model.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	
	

	Optional<User> findByUsernameAndPassword(String username, String password);
	
	User findByUserId(int UserId);
	
	int countByUsername(String username);



	
}
