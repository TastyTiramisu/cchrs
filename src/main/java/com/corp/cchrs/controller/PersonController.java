package com.corp.cchrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.corp.cchrs.service.PersonService;

@Controller
public class PersonController {
	
	@Autowired
	PersonService pService;
	
	@GetMapping("/people")
	public String getPeopleTable(Model model) {
		model.addAttribute("people", pService.getPeople());
		model.addAttribute("role", SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString());
		return "showpeople";
	}
}
