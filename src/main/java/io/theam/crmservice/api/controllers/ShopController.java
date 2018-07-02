package io.theam.crmservice.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.theam.crmservice.api.dtos.ShopDto;
import io.theam.crmservice.api.entities.Shop;
import io.theam.crmservice.api.response.Response;
import io.theam.crmservice.api.services.ShopService;

@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = "*")
public class ShopController {

	private static final Logger log = LoggerFactory.getLogger(ShopController.class);

	@Autowired
	private ShopService shopService;
	
	public ShopController() {
		
	}
	
	/**
	 * Return a shop given the Shop Name
	 * 
	 * @param shopName
	 * @return ResponseEntity<Response<ShopDto>>
	 */
	@GetMapping(value = "/shop-name/{shopName}")
	public ResponseEntity<Response<ShopDto>> searchByShopName(@PathVariable("shopName") String shopName){
		log.info("Searching shop by Name: {}", shopName);
		Response<ShopDto> response = new Response<ShopDto>();
		Optional<Shop> shop = shopService.searchByShopName(shopName);
		
		if(!shop.isPresent()) {
			log.info("Shop not found given the following name: {}", shopName);
			response.getErrors().add("Shop not found with the name: " + shopName);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.convertShopDto(shop.get()));
		return ResponseEntity.ok(response);

	}
	
	/**
	 * Return a shop given the Shop ID
	 * 
	 * @param id
	 * @return ResponseEntity<Response<ShopDto>>
	 */
	@GetMapping(value = "/shop-id/{id}")
	public ResponseEntity<Response<ShopDto>> searchById(@PathVariable("id") Long id){
		log.info("Searching shop by id: {}", id);
		Response<ShopDto> response = new Response<ShopDto>();
		Optional<Shop> shop = shopService.searchById(id);
		
		if(!shop.isPresent()) {
			log.info("Shop not found given the following id: {}", id);
			response.getErrors().add("Shop not found with the id: " + id);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.convertShopDto(shop.get()));
		return ResponseEntity.ok(response);

	}

	/**
	 * Populates a DTO with the shop data
	 * 
	 * @param shop
	 * @return ShopDto
	 */
	private ShopDto convertShopDto(Shop shop) {
		ShopDto shopDto = new ShopDto();
		shopDto.setId(shop.getId());
		shopDto.setShopName(shop.getShopName());
		
		return shopDto;
	}
	
}
