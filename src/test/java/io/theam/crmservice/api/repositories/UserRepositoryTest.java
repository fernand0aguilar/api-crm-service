package io.theam.crmservice.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import io.theam.crmservice.api.entities.Shop;
import io.theam.crmservice.api.entities.User;
import io.theam.crmservice.api.enums.ProfileEnum;
import io.theam.crmservice.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {
	
	private static final String name = "John";
	private static final String surname = "Doe";
	private static final String email = "john@doe.com";


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ShopRepository shopRepository;	
		
	@Before
	public void setUp() throws Exception {
		Shop shop = this.shopRepository.save(getShopData());
		this.userRepository.save(getUserData(shop));
	}
	
	@After
	public final void tearDown() {
		this.shopRepository.deleteAll();
	}
	
	@Test
	public void testSearchUserByName() {
		User user = this.userRepository.findByName(name);
		assertEquals(name, user.getName());
	}
	@Test
	public void testSearchUserBySurname() {
		User user = this.userRepository.findBySurname(surname);
		assertEquals(surname, user.getSurname());
	}
	@Test
	public void testSearchUserByEmail() {
		User user = this.userRepository.findByEmail(email);
		assertEquals(email, user.getEmail());
	}
	@Test
	public void testSearchUserByNameAndEmail() {
		User user = this.userRepository.findByNameOrEmail(name, email);
		assertNotNull(user);
	}
	@Test
	public void testSearchUserByNameAndEmailForEmailInvalid(){
		User user = this.userRepository.findByNameOrEmail(name, "email@invalid.com");
		assertNotNull(user);
	}
	@Test
	public void testSearchUserByNameAndEmailForNameInvalid() {
		User user = this.userRepository.findByNameOrEmail("Invalid", email);
		assertNotNull(user);
	}
	
	
	private User getUserData(Shop shop) throws NoSuchAlgorithmException{
		User user = new User();
		user.setName(name);
		user.setSurname(surname);
		user.setEmail(email);
		user.setProfile(ProfileEnum.ROLE_USER);
		user.setPassword(PasswordUtils.generatesBCrypt("123456"));
		user.setShop(shop);
		return user;
	}
	
	private Shop getShopData() {
		Shop shop = new Shop();
		shop.setShopName("Sport Shop");
		return shop;
	}
	
}
