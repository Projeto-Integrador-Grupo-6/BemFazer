package com.generation.bemfazer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.bemfazer.model.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

	public List<Servico> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);

}
