package com.example.casadoslivros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.casadoslivros.model.Livro;
import com.example.casadoslivros.model.User;
import com.example.casadoslivros.repository.LivroRepository;
import com.example.casadoslivros.service.LivroService;

@Controller
@RequestMapping("home")
public class HomeController {

	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private LivroService livroService;
	
	
	@GetMapping
	public String home(Model model) {
		String url = "home";
		model.addAttribute("url", url);
		
		return homeOnePage(model, 1);
	}
	
	@GetMapping("page/{pageNumber}")
	public String homeOnePage(Model model, @PathVariable("pageNumber") int currentPage) {
		String url = "home";
		model.addAttribute("url", url);
		 
		Page<Livro> page = livroService.findPage(currentPage);
		
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<Livro> livros = page.getContent();
		
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("livros", livros);
		
		String paginaSelecionada = Integer.toString(currentPage);
		model.addAttribute("paginaSelecionada", paginaSelecionada);
		
		 if(livros.isEmpty() || livros.size() == 0) {
				return "homeVazia";
			}
		 return "home";
	}
	
	@GetMapping("register")
	public String viewSignUpPage(Model model) {
		model.addAttribute("user", new User());
		return "usuario/register.html";
	}
	
	@GetMapping("login")
	public String viewLoginPage() {
		return "usuario/login";
	}
	
	
	
	
	
	
	
	
	
}
