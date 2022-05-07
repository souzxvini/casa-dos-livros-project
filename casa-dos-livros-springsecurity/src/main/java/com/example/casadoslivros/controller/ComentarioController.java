package com.example.casadoslivros.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.casadoslivros.dto.RequisicaoNovoComentario;
import com.example.casadoslivros.model.Comentario;
import com.example.casadoslivros.model.User;
import com.example.casadoslivros.repository.ComentarioRepository;
import com.example.casadoslivros.repository.UserRepository;

@Controller
@RequestMapping("comentario")
public class ComentarioController {

	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("homeComentarios")
	public String homeComentarios(RequisicaoNovoComentario requisicao, Model model) {
		List<Comentario> comentarios = comentarioRepository.findAll(Sort.by("id").descending());
		model.addAttribute("comentarios", comentarios);
		
		String url = "comentarios";
		 model.addAttribute("url", url);
		if(comentarios.isEmpty() || comentarios.size() == 0) {
			
			return "comentario/homeVaziaComentarios";
		}
		return "comentario/homeComentarios";
	}
	
	@GetMapping("listarTodos")
	public String listarTodosComentarios(RequisicaoNovoComentario requisicao, Model model) {
		List<Comentario> comentarios = comentarioRepository.findAll(Sort.by("id").descending());
		model.addAttribute("comentarios", comentarios);
		
		String url = "comentarios";
		 model.addAttribute("url", url);
		if(comentarios.isEmpty() || comentarios.size() == 0) {
			
			return "comentario/homeVaziaComentarios";
		}
		return "comentario/homeComentarios";
	}
	
	@GetMapping("listarPorUsuario")
	public String listarPorUsuario(RequisicaoNovoComentario requisicao, Model model, Principal principal) {
		
		String name = principal.getName();
		User user = userRepository.findByEmail(name);
		List<Comentario> comentarios = comentarioRepository.findAllByUser(user,Sort.by("id").descending());
		model.addAttribute("comentarios", comentarios);
		
		String url = "comentariosUsuario";
		 model.addAttribute("url", url);
		if(comentarios.isEmpty() || comentarios.size() == 0) {
			
			return "comentario/homeVaziaComentariosUsuario";
		}
		return "comentario/homeComentarios";
	}
	
	@GetMapping("excluir/{id}")
	public String excluirComentario(Comentario comentario, BindingResult result) {
		
		comentarioRepository.deleteById(comentario.getId());
		
		return "redirect:/comentario/homeComentarios";
	}
	
	@GetMapping("formComentario")
	public String formComentario(RequisicaoNovoComentario requisicao, Model model) {
		String url = "comentarios";
		 model.addAttribute("url", url);
		return "comentario/formComentario";
	}
	
	@PostMapping("novo")
	public String novoComentario(@Valid RequisicaoNovoComentario requisicao, BindingResult result, Model model) {
		String url = "comentarios";
		 model.addAttribute("url", url);
		 
		if(result.hasErrors()){
			return "comentario/formComentario";
		}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User usuario = userRepository.getUserByEmail(username);
		Comentario comentario = requisicao.toComentario();
		comentario.setUser(usuario);
		comentarioRepository.save(comentario);
		
		model.addAttribute("usuario", usuario);
		return "redirect:/comentario/homeComentarios";
	}
	
	
}