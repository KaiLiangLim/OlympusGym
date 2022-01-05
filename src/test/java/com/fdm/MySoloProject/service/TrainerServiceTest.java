package com.fdm.MySoloProject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdm.MySoloProject.dal.TrainerRepository;
import com.fdm.MySoloProject.model.Trainer;

public class TrainerServiceTest {

	TrainerService trainerService;

	@Mock
	TrainerRepository mockTrainerRepo;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		trainerService = new TrainerService(mockTrainerRepo);
	}
	
	@Test
	public void when_pass_In_list_of_trainer_return_listOfTrainer() {
		//Arrange
		Trainer testTrainer1 = new Trainer("bob", 12345);
		Trainer testTrainer2 = new Trainer("pete", 54321);	
		List<Trainer> listOfTestTrainer = new ArrayList<>();
		listOfTestTrainer.add(testTrainer1);
		listOfTestTrainer.add(testTrainer2);
		when(mockTrainerRepo.findAll()).thenReturn(listOfTestTrainer);
		
		//Act
		List<Trainer> result = trainerService.getAllTrainers();
		
		//Result
		verify(mockTrainerRepo,times(1)).findAll();
		assertEquals(result, listOfTestTrainer);
	}

	@Test
	public void when_passIn_trainer_name_return_trainerObject() {
		//Arrange
		Trainer testTrainer1 = new Trainer("bob", 12345);
		when(mockTrainerRepo.findByTrainerName("bob")).thenReturn(testTrainer1);
		//Act
		Trainer result = trainerService.getTrainer("bob");
		
		//Assert
		verify(mockTrainerRepo,times(1)).findByTrainerName("bob");
		assertEquals(result, testTrainer1);
	}
}
