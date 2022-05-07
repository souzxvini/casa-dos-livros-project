package com.example.casadoslivros.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.casadoslivros.dto.RequisicaoNovoLivro;
import com.example.casadoslivros.model.Genero;
import com.example.casadoslivros.model.Livro;
import com.example.casadoslivros.repository.GeneroRepository;
import com.example.casadoslivros.repository.LivroRepository;
@Controller
@RequestMapping("livro")
public class LivroController {

	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@GetMapping("excluir/{id}")
	public String excluirLivro(Livro livro , BindingResult result) {
		
		livroRepository.deleteById(livro.getId());
		
		return "redirect:/home";
	}
	
	@GetMapping("form")
	public String formLivro(RequisicaoNovoLivro requisicao, Model model) {
		List<Genero> generos = generoRepository.findAll();
		model.addAttribute("generos", generos);
		
		if(generos == null || generos.isEmpty()) {
			return "livro/erroSemGenero";
		}
		
		String url = "home";
		 model.addAttribute("url", url);
		 
		return "livro/formNovoLivro";
	}
	
	@PostMapping("novo")
	public String novoLivro(@Valid RequisicaoNovoLivro requisicao, BindingResult result, Model model) {
		
		List<Genero> generos = generoRepository.findAll();
		model.addAttribute("generos", generos);
		
		String url = "home";
		 model.addAttribute("url", url);
		
		if(result.hasErrors()){
			
			return "livro/formNovoLivro";
		}
		Livro livro = requisicao.toLivro();
		
		Optional<Genero> genero = generoRepository.findById(requisicao.getGeneroId());
        livro.setGenero(genero.get());
        
		livroRepository.save(livro);
		return "redirect:/home";
	}
	
	@GetMapping("editar/{id}")
	public String atualizarLivro(@PathVariable("id")  Long id, Model model) {
		
		
		
		List<Genero> generos = generoRepository.findAll();
		Optional<Genero> genero = generoRepository.findById(id);
		Optional<Livro> livro = livroRepository.findById(id);
		List<Livro> livros = livroRepository.findAll();
		

		if (livro.isEmpty()) {
			throw new IllegalArgumentException("Pedido invÃ¡lido");
		} else {
			model.addAttribute("generos", generos);
			model.addAttribute("genero", genero);
			model.addAttribute("livro", livro);
			model.addAttribute("livros", livros);
		}
		return "livro/formEditarLivro";
	}
	
	@PostMapping("atualizar")
	public String recadastrar(Livro livro, BindingResult result) {
	
		if(result.hasErrors()) {
			return "redirect:/livro/formEditarlivro";
		}else {
			livroRepository.save(livro);
			return "redirect:/home"	;
		}
		
	}
	
	@GetMapping("listar/{genero}")
	public String listarLivrosGenero(@PathVariable("genero") String genero, Model model) {
		List<Livro> livros = livroRepository.findAllByGeneroNome(genero, Sort.by("id").descending());
		
		String urlGenero= genero;
		 model.addAttribute("urlGenero", urlGenero);
		 
		 String  url= "generos";
		 model.addAttribute("url", url);
		
		 model.addAttribute("livros", livros);
		  if(livros.isEmpty() || livros.size() == 0) {
			return "livro/homeVaziaLivrosGenero";
		}
		 return "livro/listarLivrosPorGenero";
	}
	
}