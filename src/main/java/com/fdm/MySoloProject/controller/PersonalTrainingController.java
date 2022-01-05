package com.fdm.MySoloProject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.fdm.MySoloProject.model.PersonalTraining;
import com.fdm.MySoloProject.service.PersonalTrainingService;

@Controller
public class PersonalTrainingController {

	private PersonalTrainingService pTService;
	
	@Autowired
	public PersonalTrainingController(PersonalTrainingService pTService) {
		super();
		this.pTService = pTService;
	}
	
	/**
	 * sends user to personalTraining page
	 * @param session
	 * @return
	 */
	@GetMapping(value= "getPT")
	public String showPT(HttpSession session) {
		List<PersonalTraining> allPT = pTService.getListOfPT();
		session.setAttribute("allPT", allPT);
		return "personalTraining";
	}
	
}
