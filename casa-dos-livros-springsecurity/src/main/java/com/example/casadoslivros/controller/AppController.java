package com.example.casadoslivros.controller;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@GetMapping("register")
	public String viewSignUpPage(Model model) {
		model.addAttribute("user", new User());
		return "register.html";
	}
	
	@GetMapping("login")
	public String viewLoginPage() {
		return "login.html";
	}
	
}
