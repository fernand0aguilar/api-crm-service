package io.theam.crmservice.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import io.theam.crmservice.api.entities.Shop;

@Transactional(readOnly = true)
public interface ShopRepository extends JpaRepository<Shop, Long>{

	Shop findByShopName(String shopName);
}
