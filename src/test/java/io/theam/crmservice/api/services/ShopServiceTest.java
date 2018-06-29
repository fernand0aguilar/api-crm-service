package io.theam.crmservice.api.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.theam.crmservice.api.entities.Shop;
import io.theam.crmservice.api.repositories.ShopRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ShopServiceTest {
	
	@MockBean
	private ShopRepository shopRepository;
	
	@Autowired
	private ShopService shopService;
	
	private static final String NAME = "Sport Shop";
	
	@Before
	public void setUp() throws Exception{
		BDDMockito.given(this.shopRepository.findByShopName(Mockito.anyString())).willReturn(new Shop());
		BDDMockito.given(this.shopRepository.save(Mockito.any(Shop.class))).willReturn(new Shop());
	}
	
	@Test
	public void testSearchShopByShopName() {
		Optional<Shop> shop = this.shopService.searchByShopName(NAME);
		assertTrue(shop.isPresent());
	}
	
	@Test
	public void testPersistShop() {
		Shop shop = this.shopService.persist(new Shop());
		assertNotNull(shop);
	}
}
