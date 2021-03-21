package com.timeController.timeController.controller;

import com.timeController.timeController.dao.UserRepository;
import com.timeController.timeController.model.User;
import com.timeController.timeController.service.UserRegisterService;

//import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class HomeController {
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	UserRepository userRepo;
	@Autowired
	UserRegisterService regUser;

	@GetMapping(value = "")
	public String home() {
		return "home";
		
	}
	@GetMapping(value = "/user")
	public User getUserList() {
		return null;
	}

	@PostMapping(value = "/user")
	public void registerUser(@RequestBody User userPojo) {
		// This route is responsible for register the user 
		logger.debug("registering the user data as : ");
		logger.debug("First name : "+userPojo.getFirst_name()+"\nSecond name : "+userPojo.getSecond_name());
		logger.debug("Email :"+userPojo.getEmail());

		regUser.register(userPojo);
	}
}
