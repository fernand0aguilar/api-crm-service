package io.theam.crmservice.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.theam.crmservice.api.dtos.UserDto;
import io.theam.crmservice.api.entities.User;
import io.theam.crmservice.api.response.Response;
import io.theam.crmservice.api.services.UserService;
import io.theam.crmservice.api.utils.PasswordUtils;

//TODO -> picture
//TODO -> father id

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	public UserController() {
		
	}
	
	//TODO -> WRITE METHODS CREATE, READ, DELETE
	
	
	/**
	 * Update the User data
	 * 
	 * @param id
	 * @param userDto
	 * @param result
	 * @return ResponseEntity<Response<UserDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping(value = "/update-user/{id}")
	public ResponseEntity<Response<UserDto>> update(@PathVariable("id") Long id,
			@Valid @RequestBody UserDto userDto, 
			BindingResult result) throws NoSuchAlgorithmException{
		
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
		
		if(userDto.getPassword().isPresent()) {
			user.setPassword(PasswordUtils.generatesBCrypt(userDto.getPassword().get()));
		}
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
		//TODO -> father, photo
		
		return userDto;
	}
}
