package io.theam.crmservice.api.services;

import java.util.List;
import java.util.Optional;

import io.theam.crmservice.api.entities.User;

public interface UserService {

	/**
	 * Persist an User in the Database.
	 * 
	 * @param user
	 * @return User
	 */
	User persist(User user);
	
	/**
	 * Search and Return an user given the name.
	 * 
	 *  @param name
	 *  @return Optional<User>
	 */
	Optional<User> searchByName(String name);
	
	/**
	 * Search and return an user given an email.
	 * 
	 * @param email
	 * return Optional<User>
	 */
	Optional<User> searchByEmail(String email);
	
	/**
	 * Search and Return an User given the ID.
	 * 
	 * @param id
	 * @return Optional<User>
	 */
	Optional<User> searchById(Long id);
	
	/**
	 * Get and return all Users
	 * @return List<User>
	 */
	List<User> searchAll();

}
