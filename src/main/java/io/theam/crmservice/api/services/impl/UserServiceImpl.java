package io.theam.crmservice.api.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
	
import io.theam.crmservice.api.entities.User;
import io.theam.crmservice.api.repositories.UserRepository;
import io.theam.crmservice.api.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public User persist(User user) {
		log.info("Persisiting user: {}", user);
		return this.userRepository.save(user);
	}
	
	public Optional<User> searchByName(String name){
		log.info("Searching user by name: {}", name);
		return Optional.ofNullable(this.userRepository.findByName(name));
	}
	public Optional<User> searchByEmail(String email){
		log.info("Searching user by email: {}", email);
		return Optional.ofNullable(this.userRepository.findByEmail(email));
	}

	public Optional<User> searchById(Long id) {
		log.info("Searching user by id: {}", id);
		return this.userRepository.findById(id);
	}

	public List<User> searchAll() {
		log.info("Searching all users");
		return userRepository.findAll();
	}

	public void remove(Long id) {
		log.info("Removing the user with ID {}", id);
		this.userRepository.deleteById(id);		
	}

}
