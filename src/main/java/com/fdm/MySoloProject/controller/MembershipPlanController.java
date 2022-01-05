package com.fdm.MySoloProject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MembershipPlanController {

	@GetMapping(value= "/getMembershipPlanPage")
	public String MembershipPlanPage(HttpSession session) {
		return "membershipPlan";
	}
}
