package com.generation.bemfazer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.bemfazer.model.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
	
	public Optional <Usuario> findByEmail(String usuario);

}
