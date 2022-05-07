package com.example.casadoslivros.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.casadoslivros.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByName(String string);

	
}
