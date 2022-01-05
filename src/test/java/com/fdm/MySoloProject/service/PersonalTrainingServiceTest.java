package com.fdm.MySoloProject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdm.MySoloProject.dal.MembershipPlansRepository;
import com.fdm.MySoloProject.dal.PersonalTrainingRepository;
import com.fdm.MySoloProject.dal.TrainerRepository;
import com.fdm.MySoloProject.model.MembershipPlans;
import com.fdm.MySoloProject.model.PersonalTraining;
import com.fdm.MySoloProject.model.Trainer;

public class PersonalTrainingServiceTest {
	

	PersonalTrainingService pTService;

	@Mock
	PersonalTrainingRepository mockPTRepo;
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		pTService = new PersonalTrainingService(mockPTRepo);
	}
	
	@Test
	public void when_pass_In_method_return_listOfPT() {
		//Arrange
		PersonalTraining testPT1 = new PersonalTraining(99, "karate", "chop your chicken chop", null, null);
		PersonalTraining testPT2 = new PersonalTraining(999, "kungfu", "poke your eyeball", null, null);
		List<PersonalTraining> listOfPT = new ArrayList<>();
		listOfPT.add(testPT1);
		listOfPT.add(testPT2);
		when(mockPTRepo.findAll()).thenReturn(listOfPT);
		
		//Act
		List<PersonalTraining> result = pTService.getListOfPT();
		
		//Assert
		verify(mockPTRepo,times(1)).findAll();
		assertEquals(result, listOfPT);
	}

	@Test
	public void when_passIn_ptID_return_PTObject() {
		//Arrange
		Optional<PersonalTraining> testPT = Optional.of(new PersonalTraining(99, "karate", "chop your chicken chop", null, null));
		when(mockPTRepo.findById(testPT.get().getPersonalTrainingId())).thenReturn(testPT);
		//Act
		PersonalTraining result = pTService.getPT(testPT.get().getPersonalTrainingId());
		
		//Assert
		verify(mockPTRepo,times(1)).findById(testPT.get().getPersonalTrainingId());
		assertEquals(result, testPT.get());
	}

}
