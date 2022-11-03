package com.generation.bemfazer.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.bemfazer.model.Servico;
import com.generation.bemfazer.repository.CategoriaRepository;
import com.generation.bemfazer.repository.ServicoRepository;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServicoController {

	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping("/all")
	public ResponseEntity<List<Servico>> getAll() {
		return ResponseEntity.ok(servicoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Servico> getById(@PathVariable Long id) {
		return servicoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Servico>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(servicoRepository.findAllByTituloContainingIgnoreCase(titulo));

	}

	@PostMapping
	public ResponseEntity<Servico> post(@Valid @RequestBody Servico servicos) {
		if (categoriaRepository.existsById(servicos.getCategorias().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
				.body(servicoRepository.save(servicos));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

	}

	@PutMapping
	public ResponseEntity<Servico> put(@Valid @RequestBody Servico servicos) {
		if (servicoRepository.existsById(servicos.getId())) {
			
			if (categoriaRepository.existsById(servicos.getCategorias().getId()))
				return ResponseEntity.status(HttpStatus.OK)
					.body(servicoRepository.save(servicos));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Servico> servicos = servicoRepository.findById(id);

		if (servicos.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		servicoRepository.deleteById(id);

	}
}
