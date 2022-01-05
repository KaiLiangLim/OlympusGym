package com.fdm.MySoloProject.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.MySoloProject.model.Trainer;


public interface TrainerRepository extends JpaRepository<Trainer, Integer>  {

		Trainer findByTrainerName(String trainerName);
		
		
}
