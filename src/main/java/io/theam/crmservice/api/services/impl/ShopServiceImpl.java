package io.theam.crmservice.api.services.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.theam.crmservice.api.entities.Shop;
import io.theam.crmservice.api.repositories.ShopRepository;
import io.theam.crmservice.api.services.ShopService;

@Service
public class ShopServiceImpl implements ShopService {
	
	private static final Logger log = LoggerFactory.getLogger(ShopServiceImpl.class);
	
	@Autowired
	private ShopRepository shopRepository;
	
	@Override
	public Optional<Shop> searchByShopName(String shopName){
		log.info("Search a shop for the name {}", shopName);
		return Optional.ofNullable(shopRepository.findByShopName(shopName));
	}

	@Override
	public Shop persist(Shop shop) {
		log.info("Persisting shop: {}", shop);
		return this.shopRepository.save(shop);
	}
			
}
