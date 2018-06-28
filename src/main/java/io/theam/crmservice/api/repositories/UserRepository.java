package io.theam.crmservice.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import io.theam.crmservice.api.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	@Transactional(readOnly = true)
	User findByName(String name);
}
