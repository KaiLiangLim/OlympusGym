package com.fdm.MySoloProject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {

	@GetMapping(value= "/getAboutUs")
	public String AboutUsPage(HttpSession session) {
		return "aboutUs";
	}
}
