package com.fdm.MySoloProject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdm.MySoloProject.dal.MembershipPlansRepository;
import com.fdm.MySoloProject.dal.UserRepository;
import com.fdm.MySoloProject.model.MembershipPlans;
import com.fdm.MySoloProject.model.Trainer;

public class MembershipPlanServiceTest {
	
	MembershipPlanService membershipPlanService;

	@Mock
	MembershipPlansRepository mockMembershipPlanRepo;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		membershipPlanService = new MembershipPlanService(mockMembershipPlanRepo);
	}
	
	@Test
	public void when_call_list_of_trainer_return_listOfMembershipPlans() {
		//Arrange
		MembershipPlans testMP1 = new MembershipPlans(6, 6, 65);
		MembershipPlans testMP2 = new MembershipPlans(1, 12, 777);	
		List<MembershipPlans> listOfTestMP = new ArrayList<>();
		listOfTestMP.add(testMP1);
		listOfTestMP.add(testMP2);
		when(mockMembershipPlanRepo.findAll()).thenReturn(listOfTestMP);
		
		//Act
		List<MembershipPlans> result = membershipPlanService.getListOfMembershipPlans();
		
		//Result
		verify(mockMembershipPlanRepo,times(1)).findAll();
		assertEquals(result, listOfTestMP);
	}

}
