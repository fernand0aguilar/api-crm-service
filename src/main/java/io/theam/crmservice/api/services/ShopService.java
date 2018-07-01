package io.theam.crmservice.api.services;

import java.util.Optional;

import io.theam.crmservice.api.entities.Shop;

public interface ShopService {
	
	/**
	 * Persist a new shop into the database.
	 * @param shop
	 * @return Shop
	 */
	Shop persist(Shop shop);
	
	/**
	 * Return a shop given the name
	 * 
	 * @param shopName
	 * @return Optional<Shop>
	 */
	Optional<Shop> searchByShopName(String shopName);
}
