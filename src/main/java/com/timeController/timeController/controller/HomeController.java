package com.timeController.timeController.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.timeController.timeController.dao.ImageRepository;
import com.timeController.timeController.dao.UserRepository;
import com.timeController.timeController.model.AuthRequest;
import com.timeController.timeController.model.AuthResponse;
import com.timeController.timeController.model.User;
import com.timeController.timeController.model.profileImageModel;
import com.timeController.timeController.service.UserRegisterService;
import com.timeController.timeController.util.JWTUtil;
import com.timeController.timeController.util.UserModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1")
public class HomeController {
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	UserRepository userRepo;
	@Autowired
	UserRegisterService regUser;

	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	JWTUtil jwtTokenUtil;
	@Autowired
	UserModelAssembler userAssembler;
	@Autowired
	ImageRepository imageRepo;

	@GetMapping(value = "")
	public String home() {
		return "home";
		
	}

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@GetMapping(value = "/user")
	public CollectionModel<EntityModel<User>> getUserList() {
		List<EntityModel<User>> userList = userRepo.findAll().stream().map(userAssembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(userList,linkTo(methodOn(HomeController.class).getUserList()).withSelfRel());
	}

	@PostMapping(value = "/registration",consumes={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public User registerUser(@RequestBody User userPojo) {
		// This route is responsible for register the user 
		logger.debug("registering the user data as : ");
		logger.debug("First name : "+userPojo.getFirst_name()+"\nSecond name : "+userPojo.getSecond_name());
		logger.debug("Email :"+userPojo.getEmail());

		User findUser = userRepo.findByEmail(userPojo.getEmail());

		if (findUser == null) {
			regUser.register(userPojo);
		}
		return userPojo;
	}

	@GetMapping(value = "/user/{id}")
	public EntityModel<User> getUserById(@PathVariable String id) {
		Optional<User> user = (Optional<User>) userRepo.findById(Long.parseLong(id));
		return userAssembler.toModel(user.get());
		//return user;
	}

	@PostMapping(value = "/profile-image")
	public void addImage(@RequestParam MultipartFile profileImage) throws IOException {
		profileImageModel image = new profileImageModel();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepo.findByEmail(userDetails.getUsername());

		image.setImage(profileImage.getBytes());
		image.setUser(user);

		imageRepo.save(image);
		byte[] cont = image.getImage().getContent();

		return new ByteArrayResource(cont);
	}
}
