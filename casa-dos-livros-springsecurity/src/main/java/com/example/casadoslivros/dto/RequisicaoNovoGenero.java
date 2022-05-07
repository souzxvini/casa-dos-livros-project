package com.example.casadoslivros.dto;

import javax.validation.constraints.NotBlank;

import com.example.casadoslivros.model.Genero;

public class RequisicaoNovoGenero {

	@NotBlank
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Genero toGenero() {
		
		Genero genero = new Genero();
		genero.setNome(nome);
		
		return genero;
	}
	
}
