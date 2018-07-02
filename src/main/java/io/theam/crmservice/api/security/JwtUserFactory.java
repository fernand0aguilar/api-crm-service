package io.theam.crmservice.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.theam.crmservice.api.entities.User;
import io.theam.crmservice.api.enums.ProfileEnum;


public class JwtUserFactory {

	private JwtUserFactory() {
	}

	/**
	 * Converts and generates a JwtUser based on the user data.
	 * 
	 * @param user
	 * @return JwtUser
	 */
	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(),
				mapToGrantedAuthorities(user.getProfile()));
	}

	/**
	 * Converts a user profile to the type used by spring security.
	 * 
	 * @param profileEnum
	 * @return List<GrantedAuthority>
	 */
	private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
		
		return authorities;
	}

}

