package com.example.casadoslivros.controller;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.casadoslivros.model.Role;
import com.example.casadoslivros.model.User;
import com.example.casadoslivros.repository.GeneroRepository;
import com.example.casadoslivros.repository.RoleRepository;
import com.example.casadoslivros.repository.UserRepository;
@Controller
@RequestMapping("usuario")
public class UserController {

	@Autowired
	private GeneroRepository generoRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("homeUsuarios")
	public String homeGeneros(Model model) {
		
		Iterable<User> usuarios = userRepository.findAll();
		model.addAttribute("usuarios", usuarios);
		
		String url ="usuarios";
		model.addAttribute("url", url);
		
		 return "usuario/homeUsuarios";
		 
	}
	
	@PostMapping("process_register")
	public String processRegistration(User user) {
		
		Role adminRole = roleRepository.findByName("USER");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setEnabled(true);
		user.setRoles(Arrays.asList(adminRole));
		
		userRepository.save(user);
		
		return "redirect:/login";
	}

	@GetMapping("editar/{id}")
	public String atualizarUsuario(@PathVariable("id")  Long id, Model model) {
		
		Iterable<User> usuarios = userRepository.findAll();
		model.addAttribute("usuarios", usuarios);
		
		Optional<User> user = userRepository.findById(id);
		Iterable<Role> roles = roleRepository.findAll();
		model.addAttribute("roles", roles);
		
		if (user.isEmpty()) {
			throw new IllegalArgumentException("Pedido invÃ¡lido");
		} else {
			model.addAttribute("user", user);
		}
		return "usuario/formEditarUsuario";
	}
	
	@PostMapping("atualizar")
	public String recadastrar(User user, BindingResult result) {
	
		if(result.hasErrors()) {
			return "redirect:/usuario/formEditarUsuario";
		}else {
			userRepository.save(user);
			return "redirect:/usuario/homeUsuarios"	;
		}
		
	}
	
	@GetMapping("excluir/{id}")
	public String excluirUser(User user , BindingResult result) {
		
		userRepository.deleteById(user.getId());
		
		return "redirect:/home";
	}
	
}
