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

import io.theam.crmservice.api.entities.User;
import io.theam.crmservice.api.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
	
	private static final String name = "John";
	private static final String email = "john@doe.com";

	
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.userRepository.save(Mockito.any(User.class))).willReturn(new User());
		//BDDMockito.given(this.userRepository.findById(Mockito.anyLong()).get()).willReturn(new User());
		BDDMockito.given(this.userRepository.findByName(Mockito.anyString())).willReturn(new User());
		BDDMockito.given(this.userRepository.findByEmail(Mockito.anyString())).willReturn(new User());
	}
	
	@Test
	public void testPersistUser() {
		User user = this.userService.persist(new User());
		assertNotNull(user);
	}
	
//	@Test TODO --> FIX this
//	public void testSearchUserById() {
//		Optional<User> user = this.userService.searchById(1L);
//		assertTrue(user.isPresent());
//	}
	
	@Test
	public void testSearchUserByEmail() {
		Optional<User> user = this.userService.searchByEmail(email);
		assertTrue(user.isPresent());
	}
	
	@Test 
	public void testSearchUserByName() {
		Optional<User> user = this.userService.searchByName(name);
		assertTrue(user.isPresent());
	}
}
