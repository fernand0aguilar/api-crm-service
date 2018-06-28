package io.theam.crmservice.api.repositories;


import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.theam.crmservice.api.entities.Shop;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ShopRepositoryTest {
	
	@Autowired
	private ShopRepository shopRepository;
	private static final String name = "Sport Shop";
	
	@Before
	public void setUp() throws Exception {
		Shop shop = new Shop();
		shop.setShopName(name);
		this.shopRepository.save(shop);
	}
		
	@Test
	public void testSearchByName() {
		Shop shop = this.shopRepository.findByShopName(name);
		assertEquals(name, shop.getShopName());
	}
	
	@After
	public final void tearDown() {
		this.shopRepository.deleteAll();
	}
}
