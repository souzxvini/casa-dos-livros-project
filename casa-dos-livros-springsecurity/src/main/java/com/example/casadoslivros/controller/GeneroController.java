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

import com.example.casadoslivros.dto.RequisicaoNovoGenero;
import com.example.casadoslivros.model.Genero;
import com.example.casadoslivros.repository.GeneroRepository;
import com.example.casadoslivros.service.GeneroService;

@Controller
@RequestMapping("genero")
public class GeneroController {

	@Autowired
	private GeneroRepository generoRepository;
	
	@Autowired
	private GeneroService generoService;
	
	@GetMapping("homeGeneros")
	public String homeGeneros(Model model) {
		List<Genero> generos = generoRepository.findAll(Sort.by("nome").ascending());
		
		
		String url = "generos";
		 model.addAttribute("url", url);
		 model.addAttribute("generos", generos);
		 if(generos.isEmpty() || generos.size() == 0) {
				return "genero/homeVaziaGeneros";
			}
		 return "genero/homeGeneros";
	}

	@GetMapping("form")
	public String formGenero(RequisicaoNovoGenero requisicao, Model model) {
		
		String url = "generos";
		 model.addAttribute("url", url);
		return "genero/formNovoGenero";
	}
	
	@PostMapping("novo")
	public String novoGenero(@Valid RequisicaoNovoGenero requisicao, BindingResult result, Model model) {
		
		String url = "generos";
		 model.addAttribute("url", url);
		 
		if(result.hasErrors()){
			return "genero/formNovoGenero";
		}
		Genero genero = requisicao.toGenero();
		
		generoRepository.save(genero);
		return "redirect:/genero/homeGeneros";
	}
	
	@GetMapping("excluir/{id}")
	public String excluirGenero(Genero genero , BindingResult result) {
		
		generoRepository.deleteById(genero.getId());
		
		return "redirect:/genero/homeGeneros";
	}
	
	@GetMapping("editar/{id}")
	public String atualizarGenero(@PathVariable("id")  Long id, Model model) {
		
		Optional<Genero> genero = generoRepository.findById(id);

		if (genero.isEmpty()) {
			throw new IllegalArgumentException("Pedido invÃ¡lido");
		} else {
			model.addAttribute("genero", genero);
		}
		return "genero/formEditarGenero";
	}
	
	@PostMapping("atualizar")
	public String recadastrar(@Valid Genero genero, BindingResult result) {
	
		if(result.hasErrors()) {
			return "redirect:/genero/formEditarGenero";
		}
			generoRepository.save(genero);
			return "redirect:/genero/homeGeneros";
		}
		
}
