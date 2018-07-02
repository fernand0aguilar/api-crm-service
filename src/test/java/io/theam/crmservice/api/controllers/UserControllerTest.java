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

import io.theam.crmservice.api.entities.User;
import io.theam.crmservice.api.services.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	private static final String UPDATE_USER_ID_URL = "/api/update-user/id/";
	private static final Long ID = Long.valueOf(1);
	private static final String USER_NAME = "Fernando R.";
	private static final String USER_SURNAME = "Aguilar";
	private static final String USER_EMAIL = "email@theam.io";
	
	@Test
	@WithMockUser
	public void testUpdateUserByValidId() throws Exception {
		BDDMockito.given(this.userService.searchById(Mockito.anyLong()))
		.willReturn(Optional.of(this.getUserData()));
		
		mvc.perform(MockMvcRequestBuilders.put(UPDATE_USER_ID_URL + ID)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID))
				.andExpect(jsonPath("$.data.shopName", equalTo(USER_NAME)))
				.andExpect(jsonPath("$.data.surname", equalTo(USER_SURNAME)))
				.andExpect(jsonPath("$.data.email", equalTo(USER_EMAIL)))
				.andExpect(jsonPath("$.errors").isEmpty());
	}

	private User getUserData() {
		User user = new User();
		user.setId(ID);
		user.setName(USER_NAME);
		user.setSurname(USER_SURNAME);
		user.setEmail(USER_EMAIL);
		
		return user;
	}
}
