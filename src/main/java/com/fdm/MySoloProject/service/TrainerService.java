package com.fdm.MySoloProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.MySoloProject.dal.TrainerRepository;
import com.fdm.MySoloProject.model.Trainer;

@Service
public class TrainerService {

	private TrainerRepository trainerRepo;

	@Autowired
	public TrainerService(TrainerRepository trainerRepo) {
		super();
		this.trainerRepo = trainerRepo;
	}

	public List<Trainer> getAllTrainers() {
		List<Trainer> listOfTrainers = trainerRepo.findAll();
		return listOfTrainers;
	}

	public Trainer getTrainer(String trainerName) {
		return trainerRepo.findByTrainerName(trainerName);
	}

}
