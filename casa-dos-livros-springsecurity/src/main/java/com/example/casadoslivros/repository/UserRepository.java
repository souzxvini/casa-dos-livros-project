package com.example.casadoslivros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.casadoslivros.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("Select u from User u where u.username = :username")
	public User getUserByUsername(@Param("username") String username);

	User findByUsername(String name);

	public User findByEmail(String username);

	public User getUserByEmail(String username);
	
}
