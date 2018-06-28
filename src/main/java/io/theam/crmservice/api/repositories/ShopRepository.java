package io.theam.crmservice.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import io.theam.crmservice.api.entities.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long>{

	@Transactional(readOnly = true)
	Shop findByShopName(String shopName);
}
