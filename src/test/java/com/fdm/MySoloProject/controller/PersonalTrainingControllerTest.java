package com.fdm.MySoloProject.controller;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdm.MySoloProject.model.PersonalTraining;
import com.fdm.MySoloProject.service.PersonalTrainingService;


public class PersonalTrainingControllerTest {

	private PersonalTrainingController pTController;

	@Mock
	private PersonalTrainingService mockPTService;

	@Mock
	private HttpSession mockSession;

	@Mock
	private Model mockModel;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		pTController = new PersonalTrainingController(mockPTService);
	}
	
	@Test
	public void when_showPT_return_listofPT() {
		//Arrange
		List<PersonalTraining> testListPT = new ArrayList<>();
		when(mockPTService.getListOfPT()).thenReturn(testListPT);
		
		//Act
		String result = pTController.showPT(mockSession);
		
		//Assert
		InOrder order = inOrder(mockPTService, mockSession);
		order.verify(mockPTService,times(1)).getListOfPT();
		order.verify(mockSession,times(1)).setAttribute("allPT", testListPT);
		assertEquals(result, "personalTraining");
		
	}
	
	
	
}
