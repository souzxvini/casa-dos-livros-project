package com.example.casadoslivros.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.casadoslivros.model.Comentario;
import com.example.casadoslivros.model.User;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

	List<Comentario> findAllByUser(User user, Sort sort);

	List<Comentario>findAll(Sort sort);
}
