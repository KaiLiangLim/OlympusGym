package com.fdm.MySoloProject.dal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdm.MySoloProject.model.PersonalTraining;
import com.fdm.MySoloProject.model.Trainer;


public interface PersonalTrainingRepository extends JpaRepository<PersonalTraining, Integer>{
	
	PersonalTraining findByTrainer(Trainer trainer);
}
