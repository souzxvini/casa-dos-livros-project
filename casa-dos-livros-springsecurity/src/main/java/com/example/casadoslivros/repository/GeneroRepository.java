package com.example.casadoslivros.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.casadoslivros.model.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Long> {

	Optional<Genero> findByNome(String genero);

}
