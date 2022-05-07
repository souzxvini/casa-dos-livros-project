package com.example.casadoslivros.dto;

import javax.validation.constraints.NotBlank;

import com.example.casadoslivros.model.Livro;

public class RequisicaoNovoLivro {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String sinopse;
	
	@NotBlank
	private String autor;
	
	@NotBlank
	private String urlImagem;
	
	private Long generoId;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Long getGeneroId() {
		return generoId;
	}

	public void setGeneroId(Long generoId) {
		this.generoId = generoId;
	}
	
	
	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public Livro toLivro() {
		
		Livro livro = new Livro();
		livro.setNome(nome);
		livro.setSinopse(sinopse);
		livro.setAutor(autor);
		livro.setUrlImagem(urlImagem);
		
		return livro;
	}
	
}
