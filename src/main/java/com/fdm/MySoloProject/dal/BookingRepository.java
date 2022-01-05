package com.fdm.MySoloProject.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.MySoloProject.model.Booking;
import com.fdm.MySoloProject.model.PersonalTraining;
import com.fdm.MySoloProject.model.User;



public interface BookingRepository extends JpaRepository<Booking, Integer> {

	List<Booking> findByPersonalTraining(PersonalTraining personalTraining); 
	
	
	List<Booking> findByBookedUser(User bookedUser);
}
