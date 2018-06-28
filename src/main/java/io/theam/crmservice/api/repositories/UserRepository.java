package io.theam.crmservice.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import io.theam.crmservice.api.entities.User;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByName(String name);
	
	User findBySurname(String surname);
	
	User findByEmail(String email);

	User findByNameOrEmail(String name, String email);
}
