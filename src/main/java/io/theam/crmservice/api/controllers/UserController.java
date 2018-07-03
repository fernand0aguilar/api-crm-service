package io.theam.crmservice.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Streamable;

import io.theam.crmservice.api.dtos.UserDto;
import io.theam.crmservice.api.entities.Shop;
import io.theam.crmservice.api.entities.User;
import io.theam.crmservice.api.enums.ProfileEnum;
import io.theam.crmservice.api.repositories.UserRepository;
import io.theam.crmservice.api.response.Response;
import io.theam.crmservice.api.security.utils.JwtTokenUtil;

import io.theam.crmservice.api.services.UserService;
import io.theam.crmservice.api.utils.PasswordUtils;

//TODO -> picture

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
		
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public UserController() {
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<Response<UserDto>> create(HttpServletRequest request, @Valid @RequestBody UserDto userDto,
		BindingResult result) throws NoSuchAlgorithmException {	
		log.info("Registering new User: {}", userDto.toString());
		Response<UserDto> response = new Response<UserDto>();
		
		validateExistingData(userDto, result);
		User user = this.convertDtoToUser(userDto, result);
		
		String parentUsername = jwtTokenUtil.getUserfromRequest(request);
		user.setParent(parentUsername);

		if(result.hasErrors()) {
			log.error("Error validating New User Data: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.userService.persist(user);
		
		//TODO -> finish this persistence to return something valid
		return null; 
	}
	

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {	
		
		Response<String> response = new Response<String>();
		Optional<User> user = this.userService.searchById(id);
		
		if (!user.isPresent()) {
			log.info("Error on removing because User ID: {} is invalid.", id);
			response.getErrors().add("Error while removing User. None was found for the id " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		this.userService.remove(id);
		return ResponseEntity.ok(new Response<String>());
	}
	
	
	@GetMapping("/list-all")
	public List<User> listAll() {
		log.info("Searching All Users: ");
		return this.userService.searchAll();
	}
	
	@GetMapping("/list/{id}")
	public ResponseEntity<Response<UserDto>> listOne(@PathVariable("id") Long id,
			@Valid @RequestBody UserDto userDto,
		BindingResult result) throws NoSuchAlgorithmException {	
		return null;
		//return userService.searchById(id);
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/change-role/{id}")
	public ResponseEntity<Response<UserDto>> changeRole(@Valid @RequestBody UserDto userDto,
		BindingResult result) throws NoSuchAlgorithmException {	return null; }
		
	
	/**
	 * Update the User data
	 * 
	 * @param id
	 * @param userDto
	 * @param result
	 * @return ResponseEntity<Response<UserDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Response<UserDto>> update(@PathVariable("id") Long id,
			@Valid @RequestBody UserDto userDto, BindingResult result) throws NoSuchAlgorithmException{
		
		log.info("Updating user: {}", userDto.toString());
		Response<UserDto> response = new Response<UserDto>();
		
		Optional<User> user = this.userService.searchById(id);
		
		if(!user.isPresent()) {
			result.addError(new ObjectError("user", "User not found."));
		}
		
		this.updateUserData(user.get(), userDto, result);
		if(result.hasErrors()) {
			log.error("Error validating User: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors()
					.add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		
		this.userService.persist(user.get());
		response.setData(this.convertUserDto(user.get()));
		
		return ResponseEntity.ok(response);

	}
	
/// UTILITY METHODS	
	
	/**
	 * Update the user data based on data found on DTO.
	 * 
	 * @param user
	 * @param userDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void updateUserData (User user, @Valid UserDto userDto, 
			BindingResult result) throws NoSuchAlgorithmException {
		
		user.setName(userDto.getName());
		user.setSurname(userDto.getSurname());
		
		if(!user.getEmail().equals(userDto.getEmail())) {
			this.userService.searchByEmail(userDto.getEmail())
			.ifPresent(func -> result.addError(new ObjectError("email", "Email already exists.")));
			user.setEmail(userDto.getEmail());
		}
		
		if(!userDto.getPassword().isEmpty()) {
			user.setPassword(PasswordUtils.generatesBCrypt(userDto.getPassword()));
		}
	}
	
	/**
	 * Check if the current shop or user already exist in Database
	 * @param registerShopDto
	 * @param result
	 */
	private void validateExistingData(@Valid UserDto userDto, BindingResult result) {
		this.userService.searchByEmail(userDto.getEmail())
		.ifPresent(user -> result.addError(new ObjectError("user", "Email already exists.")));
	}
	
	/**
	 * Return a DTO with the user Data
	 * @param user
	 * @return UserDto
	 */
	private UserDto convertUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setSurname(user.getSurname());
		userDto.setEmail(user.getEmail());
		//TODO -> photo
		
		return userDto;
	}
	
	private User convertDtoToUser(@Valid UserDto userDto, BindingResult result) 
			throws NoSuchAlgorithmException {

			User user = new User();
			user.setName(userDto.getName());
			user.setSurname(userDto.getSurname());
			user.setEmail(userDto.getEmail());
			user.setProfile(userDto.getProfile());
			user.setPassword(PasswordUtils.generatesBCrypt(userDto.getPassword()));
			
			return user;
		}
	
}
