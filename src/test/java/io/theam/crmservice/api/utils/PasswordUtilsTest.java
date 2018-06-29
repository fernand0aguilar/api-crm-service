package io.theam.crmservice.api.utils;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilsTest {
	private static final String PASSWORD = "123456";
	private final BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
	
	@Test
	public void testNullPassword() throws Exception {
		assertNull(PasswordUtils.generatesBCrypt(null));
	}
	
	@Test
	public void testGeneratesHashPassword() throws Exception {
		String hash = PasswordUtils.generatesBCrypt(PASSWORD);
		assertTrue(bCryptEncoder.matches(PASSWORD, hash));
	}
}
