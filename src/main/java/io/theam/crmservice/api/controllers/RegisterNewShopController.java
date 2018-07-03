package io.theam.crmservice.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.theam.crmservice.api.response.Response;
import io.theam.crmservice.api.dtos.RegisterNewShopDto;
import io.theam.crmservice.api.entities.Shop;
import io.theam.crmservice.api.entities.User;
import io.theam.crmservice.api.enums.ProfileEnum;
import io.theam.crmservice.api.services.ShopService;
import io.theam.crmservice.api.services.UserService;
import io.theam.crmservice.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/create-shop")
@CrossOrigin(origins = "*")
public class RegisterNewShopController {

	private static final Logger log = LoggerFactory.getLogger(RegisterNewShopController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private ShopService shopService;
	
	public RegisterNewShopController() {
		
	}
	
	/**
	 * Register a new Shop into the system.
	 * 
	 * @param registerNewShopDto
	 * @param result
	 * @return ResponseEntity<Response<RegisterShopDto>>
	 * @throws NoSuchAlgorithmException
	 * @author fraguilar
	 *
	 */
	@PostMapping
	public ResponseEntity<Response<RegisterNewShopDto>> register (
		@Valid @RequestBody RegisterNewShopDto registerNewShopDto,
		BindingResult result) throws NoSuchAlgorithmException {
		
		log.info("Registring Shop: {}", registerNewShopDto.toString());
		Response<RegisterNewShopDto> response = new Response<RegisterNewShopDto>();
		
		validateExistingData(registerNewShopDto, result);
		Shop shop = this.convertDtoToShop(registerNewShopDto);
		User user = this.convertDtoToUser(registerNewShopDto, result);
		
		if(result.hasErrors()) {
			log.error("Error validating registring data of Shop: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.shopService.persist(shop);
		user.setShop(shop);
		this.userService.persist(user);
		
		response.setData(this.convertRegisterShopDto(user));
		return ResponseEntity.ok(response);	
	}

	
	/**
	 * Check if the current shop or user already exist in Database
	 * @param registerShopDto
	 * @param result
	 */
	private void validateExistingData(@Valid RegisterNewShopDto registerShopDto, BindingResult result) {
		this.shopService.searchByShopName(registerShopDto.getShopName())
		.ifPresent(emp -> result.addError(new ObjectError("shop", "Shop already exists.")));
		
		this.userService.searchByEmail(registerShopDto.getEmail())
		.ifPresent(user -> result.addError(new ObjectError("user", "Email already exists.")));
	}

	/**
	 * Convert DTO data for user
	 * 
	 * @param registerShopDto
	 * @param result
	 * @return User
	 * @throws NoSuchAlgorithmException
	 */
	private User convertDtoToUser(@Valid RegisterNewShopDto registerShopDto, BindingResult result) 
		throws NoSuchAlgorithmException {

		User user = new User();
		user.setName(registerShopDto.getUserName());
		user.setSurname(registerShopDto.getSurname());
		user.setEmail(registerShopDto.getEmail());
		user.setProfile(ProfileEnum.ROLE_ADMIN);
		user.setPassword(PasswordUtils.generatesBCrypt(registerShopDto.getPassword()));
		
		return user;
	}
	
	/**
	 * Convert DTO data for shop
	 * 
	 * @param registerShopDto
	 * @return Shop
	 */
	private Shop convertDtoToShop(@Valid RegisterNewShopDto registerShopDto) {
		Shop shop = new Shop();
		shop.setShopName(registerShopDto.getShopName());
		return shop;
	}
	
	/**
	 * Populates Register DTO with data for shop and User
	 * 
	 * @param user
	 * @return RegisterShopDto
	 */
	private RegisterNewShopDto convertRegisterShopDto(User user) {
		RegisterNewShopDto registerShopDto = new RegisterNewShopDto();
		registerShopDto.setId(user.getId());
		registerShopDto.setUserName(user.getName());
		registerShopDto.setSurname(user.getSurname());
		registerShopDto.setEmail(user.getEmail());
		
		registerShopDto.setShopName(user.getShop().getShopName());
		
		return registerShopDto;
	}

}
