package io.theam.crmservice.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import io.theam.crmservice.api.entities.Shop;
import io.theam.crmservice.api.services.ShopService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ShopControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ShopService shopService;
	
	private static final String SEARCH_SHOP_NAME_URL = "/api/shops/shop-name/";
	private static final Long ID = Long.valueOf(1);
	private static final String SHOP_NAME = "Agile Monkeys";
	
	@Test
	@WithMockUser
	public void testSearchShopByValidShopName() throws Exception {
		BDDMockito.given(this.shopService.searchByShopName(Mockito.anyString()))
		.willReturn(Optional.of(this.getShopData()));
		
		mvc.perform(MockMvcRequestBuilders.get(SEARCH_SHOP_NAME_URL + SHOP_NAME)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.shopName", equalTo(SHOP_NAME)))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	private Shop getShopData() {
		Shop shop = new Shop();
		shop.setId(ID);
		shop.setShopName(SHOP_NAME);
		
		return shop;
	}
}
