package com.fdm.MySoloProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.MySoloProject.dal.MembershipPlansRepository;
import com.fdm.MySoloProject.dal.PersonalTrainingRepository;
import com.fdm.MySoloProject.model.MembershipPlans;
import com.fdm.MySoloProject.model.PersonalTraining;

@Service
public class MembershipPlanService {
	
	
	private MembershipPlansRepository membershipRepo;


	@Autowired
	public MembershipPlanService(MembershipPlansRepository membershipRepo) {
		super();
		this.membershipRepo = membershipRepo;
	}
	
	public List<MembershipPlans> getListOfMembershipPlans() {
		List<MembershipPlans> ListOfMembershipPlans = membershipRepo.findAll();
		return ListOfMembershipPlans;
	}



}
