package com.example.casadoslivros.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.casadoslivros.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {

	
	List<Livro> findAllByGeneroNome(String genero, Sort sort);

	
}

	
	

