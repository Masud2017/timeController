package com.timeController.timeController.controller;

import java.util.List;
import java.util.Optional;

import com.timeController.timeController.dao.UserRepository;
import com.timeController.timeController.model.User;
import com.timeController.timeController.service.UserRegisterService;

//import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public List<User> getUserList() {
		List<User> userList = userRepo.findAll();
		return userList;
	}

	@PostMapping(value = "/user",consumes={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public User registerUser(@RequestBody User userPojo) {
		// This route is responsible for register the user 
		logger.debug("registering the user data as : ");
		logger.debug("First name : "+userPojo.getFirst_name()+"\nSecond name : "+userPojo.getSecond_name());
		logger.debug("Email :"+userPojo.getEmail());

		User findUser = userRepo.findByEmail(userPojo.getEmail());

		if (findUser.getEmail().equals(userPojo.getEmail())) {
			System.out.println("Address is already there.. You have to use somethign else");
			User error  = new User();
			error.setEmail(null);
			error.setPassword(null);
			error.setFirst_name(null);
			error.setSecond_name("Email Already exist");
			return error;
		}

		

		regUser.register(userPojo);
		return userPojo;
	}
	@GetMapping(value = "/user/{:id}")
	public User getUserById(@PathVariable Long id) {
		User user = userRepo.getOne(id);
		return user;


	}
}
