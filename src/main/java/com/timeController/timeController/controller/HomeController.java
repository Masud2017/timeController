package com.timeController.timeController.controller;

import com.timeController.timeController.model.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class HomeController {
	@GetMapping(value = "")
	public String home() {
		return "home";
		
	}
	@GetMapping(value = "/user")
	public User getUserList() {
		return null;
	}

	@PostMapping(value = "/user")
	public void registerUser(@PathVariable(name="email") String email, @PathVariable(name = "password") String password) {
		// This route is responsible for register the user 
	}
}
