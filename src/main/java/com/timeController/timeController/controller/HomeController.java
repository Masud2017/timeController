package com.timeController.timeController.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.timeController.timeController.dao.ImageRepository;
import com.timeController.timeController.dao.UserRepository;
import com.timeController.timeController.model.AuthRequest;
import com.timeController.timeController.model.AuthResponse;
import com.timeController.timeController.model.User;
import com.timeController.timeController.model.UserInfo;
import com.timeController.timeController.model.profileImageModel;
import com.timeController.timeController.service.ProfileImageService;
import com.timeController.timeController.service.UserRegisterService;
import com.timeController.timeController.util.JWTUtil;
import com.timeController.timeController.util.UserModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins="http://localhost:3000")
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
	@Autowired
	ProfileImageService profileImageService;
	@Autowired
	ImageRepository imgRepo;

	@GetMapping(value = "")
	public String home() {
		return "home";
		
	}

	//@CrossOrigin(origins = "http://localhost:4444")
	
	@PostMapping(value = "/authenticate")
	@CrossOrigin(origins= "http://localhost:4444/api/v1/authenticate")
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

	//@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping(value = "/registration",consumes={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public void registerUser(@RequestBody User userPojo) {
		// This route is responsible for register the user 
		logger.debug("registering the user data as : ");
		logger.debug("First name : "+userPojo.getFirst_name()+"\nSecond name : "+userPojo.getSecond_name());
		logger.debug("Email :"+userPojo.getEmail());

		User findUser = userRepo.findByEmail(userPojo.getEmail());

		if (findUser == null) {
			regUser.register(userPojo);
		}
		//return userPojo;
		//return new ResponseEntity<String>("New user registered",HttpStatus.CREATED);
	}

	@GetMapping(value = "/user/{id}")
	public EntityModel<User> getUserById(@PathVariable String id) {
		Optional<User> user = (Optional<User>) userRepo.findById(Long.parseLong(id));

		System.out.println("From specific user : "+user.get().getFirst_name());
		return userAssembler.toModel(user.get());
	}

	@PostMapping(value ="/profile-image")
	@CrossOrigin(origins= "http://localhost:4444/api/v1/profile-image")
    public ResponseEntity<?> addProfileImage(@RequestParam("profileImage") MultipartFile image) throws IOException {
        System.out.println("Printing from the todo app : "+image.getOriginalFilename());

        profileImageService.setImageName(image.getOriginalFilename());
        profileImageService.setImageContent(image);
        profileImageService.writeImageToDisk();

        return new ResponseEntity<String>("Profile image saved",HttpStatus.CREATED);
    }

	@GetMapping(value = "/whoami")
	public UserInfo whoAmI() {
		UserDetails currentUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User userWithPass = userRepo.findByEmail(currentUser.getUsername());
		UserInfo user = new UserInfo();

		user.setName(userWithPass.getFirst_name()+" "+userWithPass.getSecond_name());
		System.out.println("First name  :"+userWithPass.getSecond_name());
		user.setEmail(userWithPass.getEmail());

		return user;
	}

	@GetMapping(value = "/profile-image",produces=MediaType.IMAGE_GIF_VALUE)
	public ResponseEntity<String> getTheProfileImage() throws IOException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepo.findByEmail(userDetails.getUsername());

		profileImageModel imgModel = imgRepo.findByUser(user);
		File fp = new File(imgModel.getImageName());

		FileInputStream in = new FileInputStream(fp);
		byte[] imgBinary = in.readAllBytes();

		ByteArrayResource imageInputStream = new ByteArrayResource(imgBinary);

		// System.out.println("the name of the file is : "+imgModel.getImageName());
		String base64Version = Base64.getEncoder().encodeToString(imageInputStream.getByteArray());

		// System.out.println(base64Version);
		System.out.println("Printing from profile image first name : "+user.getFirst_name());


		// return ResponseEntity.status(HttpStatus.OK).contentLength(imageInputStream.contentLength()).body(imageInputStream);
		return ResponseEntity.status(HttpStatus.OK).contentLength(base64Version.length()).body(base64Version);

	}

	@GetMapping(value="/testJson")
	public User testJson() {
		User user = new User();
		user.setEmail("msmasud578@gmail.com");
		user.setPassword("jpmasudxp");
		user.setFirst_name("Masud karim");
		user.setSecond_name("Karim");
		return user;
	}
}
