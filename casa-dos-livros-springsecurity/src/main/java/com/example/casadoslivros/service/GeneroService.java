package com.example.casadoslivros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.casadoslivros.model.Genero;
import com.example.casadoslivros.repository.GeneroRepository;

@Service
public class GeneroService {

	@Autowired
	private GeneroRepository generoRepository;
	
	public Page<Genero> findPage(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber -1 , 12);
		
		return generoRepository.findAll(pageable);
		
	}
	
}
