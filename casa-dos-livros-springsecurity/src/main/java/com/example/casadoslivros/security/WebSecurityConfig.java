package com.example.casadoslivros.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource datasource;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
//			.antMatchers("/").hasAnyAuthority("USER", "EDITOR", "ADM")
			.antMatchers("/home", "/register", "/usuario/process_register").permitAll()
			.antMatchers("/home").hasAnyAuthority("USER", "EDITOR", "ADM")
			.antMatchers("/genero/homeGeneros").hasAnyAuthority( "USER","EDITOR", "ADM")
			.antMatchers("/comentario/homeComentarios").hasAnyAuthority("USER", "EDITOR", "ADM")
			.antMatchers("/genero/form").hasAuthority("ADM")
			.antMatchers("/livro/form").hasAuthority("ADM")
			.antMatchers("/comentario/formComentario").hasAuthority("USER")
	        .antMatchers("/livro/novo").hasAuthority("ADM")
	        .antMatchers("/genero/novo").hasAuthority("ADM")
	        .antMatchers("/comentario/novo").hasAuthority("USER")
	        .antMatchers("/livro/excluir/**").hasAnyAuthority("ADM")
	        .antMatchers("/genero/excluir/**").hasAnyAuthority("ADM")
	        .antMatchers("/comentario/excluir/**").hasAnyAuthority("ADM")
	        .antMatchers("/livro/editar/**").hasAnyAuthority("ADM", "EDITOR")
	        .antMatchers("/genero/editar/**").hasAnyAuthority("ADM", "EDITOR")
	        .antMatchers("/livro/listar/**").hasAnyAuthority("USER","ADM", "EDITOR")
	        .antMatchers("/livro/atualizar/**").hasAnyAuthority("ADM", "EDITOR")
	        .antMatchers("/genero/atualizar/**").hasAnyAuthority("ADM", "EDITOR")
			.anyRequest().authenticated()
			.and()
			.formLogin()
					.permitAll()
					.loginPage("/login")
					.usernameParameter("email")
					.passwordParameter("password")
					.loginProcessingUrl("/doLogin")
					.defaultSuccessUrl("/home", true)
					.permitAll()
				.and()
				.exceptionHandling().accessDeniedPage("/403");
			;
	}
	
	
	
}
