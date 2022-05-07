package com.example.casadoslivros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.casadoslivros.model.Livro;
import com.example.casadoslivros.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;
	
	public Page<Livro> findPage(int pageNumber){
		Pageable pageable = PageRequest.of(pageNumber -1 , 4);
		
		return livroRepository.findAll(pageable);
	}
}
