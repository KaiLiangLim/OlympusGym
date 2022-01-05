package com.fdm.MySoloProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.MySoloProject.dal.PersonalTrainingRepository;
import com.fdm.MySoloProject.model.PersonalTraining;

@Service
public class PersonalTrainingService {
	private PersonalTrainingRepository pTRepo;


	@Autowired
	public PersonalTrainingService(PersonalTrainingRepository pTRepo) {
		super();
		this.pTRepo = pTRepo;

	}


	public List<PersonalTraining> getListOfPT() {
		List<PersonalTraining> ListOfPT = pTRepo.findAll();
		return ListOfPT;
	}
	
	public PersonalTraining getPT(int personalTrainingId) { //same with type of Pt found or not
		Optional<PersonalTraining> foundPT = pTRepo.findById(personalTrainingId);
		return foundPT.get();
	}
	


}
